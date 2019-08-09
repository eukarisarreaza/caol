package com.agence.caol.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agence.caol.R
import com.agence.caol.models.DataMesUser
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_barra.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import android.graphics.Color
import android.util.Log
import kotlin.collections.ArrayList
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.components.XAxis
import java.util.*


class BarraActivity : AppCompatActivity() {
    private val TAG by lazy { BarraActivity::class.java.name }

    companion object{
        fun newInstance(context: Context, list: List<DataMesUser>): Intent {
            val intent= Intent(context, BarraActivity::class.java)
            val type= object : TypeToken<List<DataMesUser>>(){}.type
            val parmetros = Bundle()
            parmetros.putString("data", Gson().toJson(list, type))
            intent.putExtras(parmetros)
            return intent
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barra)

        configureBarChart()


        val data = this.intent.extras!!.getString("data", "")

        if(data.isNotEmpty()){
            val type= object : TypeToken<List<DataMesUser>>(){}.type
            val list_data: List<DataMesUser> = Gson().fromJson(data, type)
            setupChart(list_data)
        }

    }


    val groupSpace = 0.4f
    val barSpace = 0.03f // x4 DataSet
    val barWidth = 0.3f // x4 DataSet

    private fun configureBarChart() {

        chart.description.isEnabled = false
        chart.setPinchZoom(false)
        chart.setDrawBarShadow(false)
        chart.setDrawGridBackground(false)
        chart.setDrawGridBackground(false)

    }

    private fun setupChart(data_user: List<DataMesUser>) {

        val meses_encontrados= ArrayList<Int>()

        val xVals = ArrayList<String>()

        data_user.forEach {
            //obtener los grupos por mes
            it.list_data.forEach {mes->

                if(!xVals.contains(mes.mes)){
                    xVals.add(mes.mes)
                }

                if(!meses_encontrados.contains(mes.num_mes)){
                    meses_encontrados.add(mes.num_mes)
                }
            }
        }


        val groupCount = xVals.size

        val list_bar_entry= ArrayList<ArrayList<BarEntry>>()

        for (i in 1..data_user.size){
            list_bar_entry.add(arrayListOf())
        }

        var cont=1
        var i=0

        data_user.forEach { user->
            meses_encontrados.forEach { mes ->
                Log.e(TAG, "cont $cont mes encontrado $mes usuario ${user.caoUsuario.no_usuario}" )
                list_bar_entry[i].add(BarEntry(cont.toFloat(), getMesUser(user, mes)))
                cont++
            }
           i++
        }


        val data = BarData()
        list_bar_entry.forEachIndexed { index, it ->
            val set1=  BarDataSet(it, data_user[index].caoUsuario.no_usuario)
            set1.color= getRandomColor()
            data.addDataSet(set1)
        }


        data.setValueFormatter(LargeValueFormatter())
        chart.data = data
        chart.barData.barWidth = barWidth
        chart.xAxis.axisMinimum = 0f
        chart.xAxis.axisMaximum = 0 + chart.barData.getGroupWidth(groupSpace, barSpace) * groupCount
        chart.groupBars(0f, groupSpace, barSpace)
        chart.data.isHighlightEnabled = false
        chart.invalidate()


        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(true)
        l.yOffset = 20f
        l.xOffset = 0f
        l.yEntrySpace = 0f
        l.textSize = 8f


        //X-axis
        /*
        val xAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.setCenterAxisLabels(true)
        xAxis.setDrawGridLines(false)
        xAxis.axisMaximum = 6f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xVals)*/

        //Y-axis
        chart.axisRight.isEnabled = false
        val leftAxis = chart.axisLeft
        leftAxis.valueFormatter = LargeValueFormatter()
        leftAxis.setDrawGridLines(true)
        leftAxis.spaceTop = 35f
        leftAxis.axisMinimum = 0f

    }

    private fun getMesUser(user: DataMesUser, mes: Int): Float {

        user.list_data.forEach {
            if(it.num_mes==mes)
                return it.neto.toFloat()
        }
        return 0f
    }


    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }


}
