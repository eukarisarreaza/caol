package com.agence.caol.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.agence.caol.R
import com.agence.caol.activities.BarraActivity
import com.agence.caol.activities.ListActivity
import com.agence.caol.activities.PieActivity
import com.agence.caol.activities.RelatorioActivity
import com.agence.caol.adapteres.AdapterClientes
import com.agence.caol.adapteres.AdapterConsultores
import com.agence.caol.adapteres.AdapterDataUsuarios
import com.agence.caol.adapteres.CustomSpinner
import com.agence.caol.models.CaoCliente
import com.agence.caol.models.CaoUsuario
import com.agence.caol.models.DataMesUser
import com.agence.caol.utilities.CONSULTA
import com.agence.caol.utilities.KEY_CLIENTE_CONSULTOR
import com.agence.caol.utilities.REQUEST_CODE_LIST_ACTIVITY
import com.agence.caol.viewmodels.MainViewModel
import com.cplus.android.utilities.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vlatam.vlatamtienda.Helper.Status
import kotlinx.android.synthetic.main.fragment_por_consultor.*
import org.koin.android.viewmodel.ext.android.viewModel


class PorConsultorFragment : Fragment() {
    private var lista_data: ArrayList<DataMesUser> = arrayListOf()
    private var list_usuario: List<CaoUsuario> = arrayListOf()
    private var list_clientes: List<CaoCliente> = arrayListOf()

    private val arrayMes by lazy { resources.getStringArray(R.array.mes) }
    private val arrayAnio by lazy { resources.getStringArray(R.array.anio)  }

    private var opcion= 1

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_por_consultor, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(context!!)

        mes_inicio.adapter= CustomSpinner(context!!, arrayMes.toList())
        mes_fin.adapter= CustomSpinner(context!!, arrayMes.toList())
        anio_inicio.adapter= CustomSpinner(context!!, arrayAnio.toList())
        anio_fin.adapter= CustomSpinner(context!!, arrayAnio.toList())

        var listener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               lista_data.clear()
            }

        }

        mes_inicio.onItemSelectedListener= listener
        mes_fin.onItemSelectedListener= listener
        anio_fin.onItemSelectedListener= listener
        anio_inicio.onItemSelectedListener= listener


        btn_add.setOnClickListener{
            startActivityForResult(ListActivity.newInstance(context!!), REQUEST_CODE_LIST_ACTIVITY)
        }

        por_consultor.setOnClickListener {
            selectedPorConsultor()
            setupAdpter(arrayListOf())
            Utils.setPreferences(context!!, KEY_CLIENTE_CONSULTOR, CONSULTA.CONSULTOR.name)
        }

        por_cliente.setOnClickListener {
            selectedPorCliente()
            setupAdpter(arrayListOf())
            Utils.setPreferences(context!!, KEY_CLIENTE_CONSULTOR, CONSULTA.CLIENTE.name)
        }

        var consulta= Utils.getPreferences(context!!, KEY_CLIENTE_CONSULTOR, CONSULTA.CONSULTOR.name,"S") as String

        if(consulta == CONSULTA.CONSULTOR.name){
            selectedPorConsultor()
        }else{
            selectedPorCliente()
        }

        relatorio.setOnClickListener {
            opcion=1
            if(lista_data.isEmpty()){
                getData()
            }else
                startActivity(RelatorioActivity.newInstance(context!!, lista_data ))
        }

        grafico.setOnClickListener {
            opcion=2
            if(lista_data.isEmpty()){
                getData()
            }else
                startActivity(BarraActivity.newInstance(context!!, lista_data))
        }
        pizza.setOnClickListener {
            opcion=3
            if(lista_data.isEmpty()){
                getData()
            }else
                startActivity(PieActivity.newInstance(context!!, lista_data))
        }
        setupRelatorio()
    }

    private fun getData(){
        if(list_usuario.isNotEmpty()){
            mainViewModel.relatorio(list_usuario, mes_inicio.selectedItemPosition+1,
                arrayAnio[anio_inicio.selectedItemPosition].toInt(), mes_fin.selectedItemPosition+1,
                arrayAnio[anio_fin.selectedItemPosition].toInt())
        } else
            Toast.makeText(context!!, "Lista de usurios vacia", Toast.LENGTH_LONG).show()
    }

    private fun setupRelatorio() {

        mainViewModel.data_grafica.observe(this, Observer {
            when(it.status){
                Status.ERROR->{
                    Toast.makeText(context!!, it.message, Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS->{
                    lista_data=it.data!! as ArrayList<DataMesUser>
                    when(opcion){
                        1->{
                            startActivity(RelatorioActivity.newInstance(context!!, it.data!! ))
                        }
                        2->{
                            startActivity(BarraActivity.newInstance(context!!, lista_data))
                        }
                        3->{
                            startActivity(PieActivity.newInstance(context!!, lista_data))
                        }
                    }
                }
            }

        })
    }

    private fun selectedPorCliente(){
        por_cliente.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        por_cliente.setTextColor(resources.getColor(R.color.white))

        por_consultor.setBackgroundColor(resources.getColor(R.color.white))
        por_consultor.setTextColor(resources.getColor(R.color.colorPrimary))

        txt_list.text= resources.getString(R.string.clientes)
        text_add.text= resources.getString(R.string.agrega_clientes)

        setupAdpterCliente(list_clientes)
    }

    private fun selectedPorConsultor(){

        por_consultor.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        por_consultor.setTextColor(resources.getColor(R.color.white))

        por_cliente.setBackgroundColor(resources.getColor(R.color.white))
        por_cliente.setTextColor(resources.getColor(R.color.colorPrimary))

        txt_list.text= resources.getString(R.string.consultores)
        text_add.text= resources.getString(R.string.agrega_consultores)

        setupAdpter(list_usuario)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("TAG", "ON ACT RESILT $requestCode")
        when(requestCode) {
            REQUEST_CODE_LIST_ACTIVITY->{
                if (resultCode == Activity.RESULT_OK) {
                    val result = data!!.getStringExtra("datos")
                    var consulta= Utils.getPreferences(context!!, KEY_CLIENTE_CONSULTOR, CONSULTA.CONSULTOR.name) as String
                    if(consulta == CONSULTA.CONSULTOR.name){
                        val listType = object : TypeToken<List<CaoUsuario>>() {}.type
                        list_usuario = Gson().fromJson(result, listType)
                        setupAdpter(list_usuario)
                    }else{
                        val listType = object : TypeToken<List<CaoCliente>>() {}.type
                        list_clientes = Gson().fromJson(result, listType)
                        setupAdpterCliente(list_clientes)
                    }

                    lista_data.clear()
                }
            }
        }
    }

    private fun setupAdpter(list_usuario: List<CaoUsuario>) {
        if(list_usuario.isNotEmpty()){
            text_add.visibility= View.GONE
        }else{
            text_add.visibility= View.VISIBLE
        }


        list_usuario.forEach {
            it.selected= false
        }

        recycler.adapter= AdapterConsultores(list_usuario, context!!){

        }
    }

    private fun setupAdpterCliente(list_clientes: List<CaoCliente>) {
        if(list_clientes.isNotEmpty()){
            text_add.visibility= View.GONE
        }else{
            text_add.visibility= View.VISIBLE
        }


        list_clientes.forEach {
            it.selected= false
        }

        recycler.adapter= AdapterClientes(list_clientes, context!!){

        }
    }
}
