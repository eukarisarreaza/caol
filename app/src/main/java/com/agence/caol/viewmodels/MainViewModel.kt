package com.agence.caol.viewmodels

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agence.caol.CaolApplication
import com.agence.caol.R
import com.agence.caol.models.CaoUsuario
import com.agence.caol.models.DataMes
import com.agence.caol.models.DataMesAnio
import com.agence.caol.models.DataMesUser
import com.agence.caol.models.database.AppDataBase
import com.google.gson.Gson
import com.vlatam.vlatamtienda.Helper.Resource
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import java.util.*

class MainViewModel : ViewModel(){
    private val TAG by lazy { MainViewModel::class.java.simpleName }
    private val database by lazy { AppDataBase.getInstance(CaolApplication.instance.getContext()) }

    var data_grafica= MutableLiveData<Resource<List<DataMesUser>>> ()

    var lista_datos_usuario= arrayListOf<DataMesUser>()
    var lista_meses_anio= arrayListOf<DataMesAnio>()


    fun relatorio(usuarios: List<CaoUsuario>, mes_inicio: Int, anio_inicio: Int, mes_fin: Int, anio_fin: Int){
        AsyncTask.execute {

            //CALCULATE UNA LISTA DE MESES EN EL PERIODO A CONSULTAR
            if(anio_inicio== anio_fin && mes_inicio>mes_fin){
                data_grafica.postValue(Resource.error(CaolApplication.instance.getContext().resources.getString(R.string.periodo_no_valido), lista_datos_usuario))
                return@execute
            }

            if(anio_inicio> anio_fin){
                data_grafica.postValue(Resource.error(CaolApplication.instance.getContext().resources.getString(R.string.periodo_no_valido), lista_datos_usuario))
                return@execute
            }
            // calcula todos los contenidos dentro del periodo
            calculateFechas(mes_inicio, anio_inicio, mes_fin, anio_fin)

            //Log.e(TAG, "LISTA FECHAS ${Gson().toJson(lista_meses_anio)}")

            lista_datos_usuario.clear()

            usuarios.forEach { us->
                var lista_user= arrayListOf<DataMes>()
                var costo_fijo= 0.0

                try {
                    val salario= database.caoSalarioDao().findById(us.co_usuario)
                    if(salario!=null){
                        costo_fijo= salario.brut_salario.toDouble()
                    }
                }catch (e: Exception){}

                lista_meses_anio.forEach { ma ->
                    var mes: Int = ma.mes
                    var anio: Int = ma.anio


                    /** CALCULO DE NETO DE USUARIO POR UN MES  **/
                    var aux_net = 0.0
                    var comision = 0.0
                    Log.e(TAG, "meees "+"/$mes/$anio")
                    //lista de facturas de un mes en especifico
                    var l=database.caoFacturaDao().getAllUser(us.co_usuario, "%/$mes/%", "%/$anio%")

                     if(l.isNotEmpty()){

                         l.forEach { f ->

                             var valor=f.valor.toDouble()
                             Log.e(TAG, "valor $valor")

                             var porc= f.total_imp_inc.toDouble()/100
                             Log.e(TAG, "porcentaje $porc")

                             var neto_fact= valor -(valor*porc)
                             Log.e(TAG, "neto factura $neto_fact")

                             var porc_com= f.comissao_cn.toDouble()/100
                             Log.e(TAG, "porcentaje comision $porc_com")

                             var comision_fact= (valor -(valor*porc))*porc_com

                             aux_net+=neto_fact
                             comision+=comision_fact
                         }
                         var lucro= aux_net-(costo_fijo+comision)

                         var data= DataMes(
                             neto = aux_net,
                             fijo = costo_fijo,
                             comision = comision,
                             lucro = lucro,
                             mes = "${getMes(mes)} $anio",
                             num_mes = mes
                         )
                         Log.e(TAG, "DATA MES ${Gson().toJson(data)}")
                         lista_user.add(data)
                     }

                }
                    /************* FIN DE USUARIO POR UN MES  *************/
                /*** calculos los totales**/
                var total_liquido=0.0
                var total_comision=0.0
                var total_fijo=0.0
                var total_lucro=0.0

                lista_user.forEach {
                    total_fijo+= it.fijo
                    total_comision+= it.comision
                    total_liquido+= it.neto
                    total_lucro+= it.lucro
                }

                lista_datos_usuario.add(DataMesUser(us, lista_user,
                    total_liquido, total_fijo, total_comision, total_lucro))
            }


            Log.e(TAG, "LISTA DATOS CON USUARIOS ${Gson().toJson(lista_datos_usuario)}")

            data_grafica.postValue(Resource.success(lista_datos_usuario))

        }
    }


    private fun calculateFechas(mes_inicio: Int, anio_inicio: Int, mes_fin: Int, anio_fin: Int){
        lista_meses_anio.clear()

        var anio_inicio_aux= anio_inicio
        var mes_inicio_aux= mes_inicio
        var anio_fin_aux= anio_fin
        var mes_fin_aux= mes_fin

        while (anio_inicio_aux<=anio_fin_aux){
            var aux_mes=mes_fin
            if(anio_inicio_aux<anio_fin_aux){
                aux_mes= 12
            }

            while (mes_inicio_aux<=aux_mes){
                lista_meses_anio.add(DataMesAnio(anio_inicio_aux, mes_inicio_aux))
                mes_inicio_aux++
            }
            anio_inicio_aux++
            mes_inicio_aux=1
        }
    }



    private fun getMes(num: Int): String{
       var calendar= Calendar.getInstance()
        calendar.set(Calendar.MONTH, num-1)
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
    }


}

val moduleMain= module{
    viewModel{ MainViewModel() }
}