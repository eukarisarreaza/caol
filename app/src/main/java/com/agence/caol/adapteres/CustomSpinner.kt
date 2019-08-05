package com.agence.caol.adapteres

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.agence.caol.R
import kotlinx.android.synthetic.main.view_simple_spinner.view.*


class CustomSpinner (val context: Context, var listItemsTxt: List<String>) : BaseAdapter() {


    val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View

        if (convertView == null) {
            view = mInflater.inflate(R.layout.view_simple_spinner, parent, false)
        } else {
            view = convertView
        }
        view.label.text = listItemsTxt[position]
        return view
    }

    override fun getItem(position: Int): String {
        return listItemsTxt[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listItemsTxt.size
    }

}