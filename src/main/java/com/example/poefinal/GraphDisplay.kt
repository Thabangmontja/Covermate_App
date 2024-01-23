package com.example.poefinal

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class GraphDisplay : AppCompatActivity() {

    lateinit var pieChart: PieChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph_display)

        pieChart=findViewById(R.id.pie_chart)


        val list:ArrayList<PieEntry> = ArrayList()

        list.add(PieEntry(5f,"Car"))
        list.add(PieEntry(9f,"Pet"))
        list.add(PieEntry(3f,"Home"))
        list.add(PieEntry(7f,"Family"))
        list.add(PieEntry(6f,"Others"))

        val pieDataSet= PieDataSet(list,"List")

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)
        pieDataSet.valueTextColor= Color.BLACK
        pieDataSet.valueTextSize=15f

        val pieData= PieData(pieDataSet)

        pieChart.data= pieData

        pieChart.description.text= "Pie Chart"

        pieChart.centerText="Goal Achievement"

        pieChart.animateY(2000)



    }

}