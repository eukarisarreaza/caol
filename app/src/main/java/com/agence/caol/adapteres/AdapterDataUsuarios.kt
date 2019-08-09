package com.agence.caol.adapteres

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.agence.caol.R
import com.agence.caol.models.DataMesUser
import kotlinx.android.synthetic.main.view_tabla.view.*

class AdapterDataUsuarios (val items: List<DataMesUser>, val context: Context,
                           val listener: (DataMesUser) -> Unit): RecyclerView.Adapter<AdapterDataUsuarios.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_tabla, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: DataMesUser, listener: (DataMesUser) -> Unit){
            view.cao_usuario.text= item.caoUsuario.no_usuario
            view.total_liquido.text= "R$ "+String.format("%.2f", item.total_liquido)
            view.total_fijo.text= "R$ "+String.format("%.2f", item.total_fijo)
            view.total_comision.text= "R$ "+String.format("%.2f", item.total_comision)
            view.total_lucro.text= "R$ "+String.format("%.2f", item.total_lucro)

            view.recycler.layoutManager= LinearLayoutManager(view.context!!)
            view.recycler.adapter= AdapterDetallesTabla(item.list_data, view.context){

            }
        }
    }
}


