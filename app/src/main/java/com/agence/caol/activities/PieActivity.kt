package com.agence.caol.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agence.caol.R
import com.agence.caol.models.DataMesUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.animation.Easing
import kotlinx.android.synthetic.main.activity_pie.*
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import kotlin.collections.ArrayList


class PieActivity : AppCompatActivity() {

    companion object{
        fun newInstance(context: Context, list: List<DataMesUser>): Intent {
            val intent= Intent(context, PieActivity::class.java)
            val type= object : TypeToken<List<DataMesUser>>(){}.type
            val parmetros = Bundle()
            parmetros.putString("data", Gson().toJson(list, type))
            intent.putExtras(parmetros)
            return intent
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie)
        toolbar.title=resources.getString(R.string.pizza)
        toolbar.setNavigationIcon(R.drawable.chevron_left)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        configurePieChart()


        val data = this.intent.extras!!.getString("data", "")

        if(data.isNotEmpty()){
            val type= object : TypeToken<List<DataMesUser>>(){}.type
            val list_data: List<DataMesUser> = Gson().fromJson(data, type)
            setupChart(list_data)
        }

    }

    private fun setupChart(list_data: List<DataMesUser>) {

        val entries = ArrayList<PieEntry>()

        list_data.forEach {
            var entry= PieEntry(it.total_liquido.toFloat(), it.caoUsuario.no_usuario)
            entries.add(entry)
        }
        var dataSet = PieDataSet(entries, "Participacion en los Ingresos")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        dataSet.valueTextColor= Color.BLACK

        // add a lot of colors

        val colors = ArrayList<Int>()
        colors.add(getRandomColor())
        colors.add(getRandomColor())
        colors.add(getRandomColor())
        colors.add(getRandomColor())
        colors.add(getRandomColor())


        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)
        chart.data = data

        //chart.xAxis.textColor= Color.BLACK
        chart.setEntryLabelColor(Color.BLACK)
        // undo all highlights
        chart.highlightValues(null)

        chart.invalidate()
    }

    private fun configurePieChart() {
        chart.setUsePercentValues(true)
        chart.getDescription().setEnabled(false)
        chart.setExtraOffsets(5f, 10f, 5f, 5f)

        chart.setDragDecelerationFrictionCoef(0.95f)

        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.WHITE)

        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)

        chart.holeRadius = 58f
        chart.transparentCircleRadius = 61f

        chart.setDrawCenterText(true)

        chart.rotationAngle = 0f
        // enable rotation of the chart by touch
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true


        chart.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);

        val l = chart.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.setXEntrySpace(7f)
        l.setYEntrySpace(0f)
        l.setYOffset(0f)

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(12f)
    }

    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

}
