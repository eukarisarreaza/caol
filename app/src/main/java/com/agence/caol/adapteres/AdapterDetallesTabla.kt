package com.agence.caol.adapteres

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agence.caol.R
import com.agence.caol.models.DataMes
import kotlinx.android.synthetic.main.view_item_tabla.view.*
import java.text.DecimalFormat

class AdapterDetallesTabla (val items: List<DataMes>, val context: Context,
                            val listener: (DataMes) -> Unit): RecyclerView.Adapter<AdapterDetallesTabla.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_item_tabla, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder (val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: DataMes, listener: (DataMes) -> Unit){
            var df = DecimalFormat("#.00")
            view.periodo.text= item.mes
            view.liquido.text= "R$ "+df.format(item.neto)
            view.costo_fijo.text= "R$ "+df.format(item.fijo)
            view.comision.text= "R$ "+df.format(item.comision)
            view.lucro.text= "R$ "+df.format(item.lucro)
        }
    }
}


