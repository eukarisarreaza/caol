package com.agence.caol.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.agence.caol.R
import com.agence.caol.adapteres.AdapterDataUsuarios
import com.agence.caol.models.DataMesUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_relatorio.*
import kotlinx.android.synthetic.main.toolbar.*

class RelatorioActivity : AppCompatActivity() {
    private val TAG by lazy { RelatorioActivity::class.java.name }

    companion object{
        fun newInstance(context: Context, list: List<DataMesUser>): Intent {
            val intent= Intent(context, RelatorioActivity::class.java)
            val type= object : TypeToken<List<DataMesUser>>(){}.type
            val parmetros = Bundle()
            parmetros.putString("data", Gson().toJson(list, type))
            intent.putExtras(parmetros)
            return intent
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relatorio)


        toolbar.title=resources.getString(R.string.relatorio)
        toolbar.setNavigationIcon(R.drawable.chevron_left)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val data = this.intent.extras!!.getString("data", "")

        if(data.isNotEmpty()){
            val type= object : TypeToken<List<DataMesUser>>(){}.type
            val list_data: List<DataMesUser> = Gson().fromJson(data, type)
            setupAdapter(list_data)
        }


    }

    private fun setupAdapter(lista: List<DataMesUser>) {
        Log.e(TAG, "numero de usuario a graficar ${lista.size}")

        recycler_tabla.layoutManager= LinearLayoutManager(this)

        recycler_tabla.adapter= AdapterDataUsuarios(lista, this){

        }
    }
}
