package com.agence.caol.viewmodels

import androidx.lifecycle.*
import com.agence.caol.CaolApplication
import com.agence.caol.models.CaoCliente
import com.agence.caol.models.CaoUsuario
import com.agence.caol.models.database.AppDataBase
import com.agence.caol.utilities.CONSULTA
import com.agence.caol.utilities.KEY_CLIENTE_CONSULTOR
import com.cplus.android.utilities.Utils
import com.vlatam.vlatamtienda.Helper.Resource
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class ConsultoresViewModel : ViewModel() {

    private val TAG by lazy { ConsultoresViewModel::class.java.simpleName }
    val consultores_list = MutableLiveData<Resource<List<CaoUsuario>>> ()
    val cliente_list = MutableLiveData<Resource<List<CaoCliente>>> ()
    private val database by lazy { AppDataBase.getInstance(CaolApplication.instance.getContext()) }


    fun getList(){
        var consulta= Utils.getPreferences(CaolApplication.instance.getContext(), KEY_CLIENTE_CONSULTOR, CONSULTA.CONSULTOR.name,"S") as String

        if(consulta == CONSULTA.CONSULTOR.name){
            consultores_list.value= Resource.loading(null)

            database.caoUsuarioDao().getAllUserPermissaoSistema().observeForever {
                if(it!=null){
                    consultores_list.value= Resource.success(it)
                }
            }

        }else{
            cliente_list.value= Resource.loading(null)

            database.caoClienteDao().getAll().observeForever {
                cliente_list.value= Resource.success(it)
            }
        }
    }


}


val moduleListConsultores= module{
    viewModel{ ConsultoresViewModel() }
}