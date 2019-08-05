package com.agence.caol.adapteres

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agence.caol.R

class AdapterBase (val items: MutableList<Any>,  val context: Context,
                        val listener: (Any) -> Unit): RecyclerView.Adapter<AdapterBase.ViewHolder>() {

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

        fun bind(item: Any, listener: (Any) -> Unit){


        }
    }
}


