package com.agence.caol.adapteres

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agence.caol.R
import com.agence.caol.models.CaoUsuario
import kotlinx.android.synthetic.main.item_cliente.view.*

class AdapterConsultores (val items: MutableList<CaoUsuario>, val context: Context,
                          val listener: (CaoUsuario) -> Unit): RecyclerView.Adapter<AdapterConsultores.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cliente, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: CaoUsuario, listener: (CaoUsuario) -> Unit){
            view.user_name.text= item.no_usuario
            view.razon.text= item.ds_endereco


            view.setOnClickListener {
                item.selected=!item.selected

                if(item.selected){
                    view.ly_parent.setBackgroundColor(view.context.resources.getColor(R.color.bag_hint))
                }else{
                    view.ly_parent.setBackgroundColor(view.context.resources.getColor(R.color.white))
                }

                listener(item)
            }

        }
    }
}


