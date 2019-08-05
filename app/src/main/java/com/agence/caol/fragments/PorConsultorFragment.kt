package com.agence.caol.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agence.caol.R
import com.agence.caol.adapteres.CustomSpinner
import kotlinx.android.synthetic.main.fragment_por_consultor.*


class PorConsultorFragment : Fragment() {
    private val arrayMes by lazy { resources.getStringArray(R.array.mes) }
    private val arrayAnio by lazy { resources.getStringArray(R.array.anio)  }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_por_consultor, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        mes_inicio.adapter= CustomSpinner(context!!, arrayMes.toList())
        mes_fin.adapter= CustomSpinner(context!!, arrayMes.toList())
        anio_inicio.adapter= CustomSpinner(context!!, arrayAnio.toList())
        anio_fin.adapter= CustomSpinner(context!!, arrayAnio.toList())
        
    }
}
