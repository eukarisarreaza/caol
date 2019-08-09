package com.agence.caol.adapteres

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agence.caol.R
import com.agence.caol.models.CaoCliente
import kotlinx.android.synthetic.main.item_cliente.view.*

class AdapterClientes (val items: List<CaoCliente>, val context: Context,
                       val listener: (CaoCliente) -> Unit): RecyclerView.Adapter<AdapterClientes.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cliente, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItemPos(item: CaoCliente): Int {
        items.forEachIndexed { index, CaoCliente ->
            if(CaoCliente == item)
                return index
        }
        return 0
    }

    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: CaoCliente, listener: (CaoCliente) -> Unit){
            view.user_name.text= item.no_contato
            view.razon.text= item.ds_endereco

            if(item.selected){
                view.ly_parent.setBackgroundColor(view.context.resources.getColor(R.color.bag_hint))
            }else{
                view.ly_parent.setBackgroundColor(view.context.resources.getColor(R.color.white))
            }

            view.setOnClickListener {
                listener(item)
            }

        }
    }
}


