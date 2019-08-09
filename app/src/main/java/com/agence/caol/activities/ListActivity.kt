package com.agence.caol.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.agence.caol.R
import com.agence.caol.adapteres.AdapterConsultores
import com.agence.caol.models.CaoUsuario
import com.agence.caol.viewmodels.ConsultoresViewModel
import com.google.gson.Gson
import com.vlatam.vlatamtienda.Helper.Status
import kotlinx.android.synthetic.main.activity_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListActivity : BaseActivity() {
    private val listViewModel: ConsultoresViewModel by viewModel()
    private lateinit var adapter: AdapterConsultores
    private var list_selected: ArrayList<CaoUsuario> = arrayListOf()

    companion object{
        fun newInstance(context: Context): Intent {
            val intent= Intent(context, ListActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupView()
        setupConsultores()
        setupClientes()
        listViewModel.getList()
    }

    private fun setupClientes() {

        listViewModel.cliente_list.observe(this, Observer {
            when(it.status){
                Status.LOADING->{
                    showProgressDialog(resources.getString(R.string.loading))
                }
                Status.SUCCESS->{
                    hideProgressDialog()

                }
                Status.ERROR->{
                    hideProgressDialog()
                }
            }
        })
    }

    private fun setupConsultores() {
        listViewModel.consultores_list.observe(this, Observer {
            when(it.status){
                Status.LOADING->{
                    showProgressDialog(resources.getString(R.string.loading))
                }
                Status.SUCCESS->{
                    hideProgressDialog()
                    adapter= AdapterConsultores(it.data!!, this){item->
                        item.selected=!item.selected

                        if(!list_selected.contains(item)){
                            list_selected.add(item)
                        }else{
                            list_selected.remove(item)
                        }

                        adapter.notifyItemChanged(adapter.getItemPos(item))
                    }
                    recycler.adapter= adapter
                }
                Status.ERROR->{
                    hideProgressDialog()
                }
            }
        })
    }

    override fun setupView() {
        recycler.layoutManager= LinearLayoutManager(this)

        check.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra("datos", Gson().toJson(list_selected))
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

}
