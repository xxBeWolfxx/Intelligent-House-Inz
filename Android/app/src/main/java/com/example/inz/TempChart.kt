package com.example.inz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAOptions
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATooltip

class TempChart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp_chart)
        val aaChartView = findViewById<AAChartView>(R.id.aa_chart_view)
        aaChartView.aa_drawChartWithChartModel(aaChartModel)

    }

    val aaChartModel : AAChartModel = AAChartModel()
        .chartType(AAChartType.Area)
        .title("Temperature")
        .backgroundColor("#2A2A2B")
        .yAxisGridLineWidth(0.00f)
        .xAxisTickInterval(1)
        .xAxisLabelsEnabled(true)
        .categories(arrayOf("00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"))
        .dataLabelsEnabled(true)
        .yAxisMin(17.00f)
        .series(arrayOf(
            AASeriesElement()
                .name("09.12.2020")
                .data(arrayOf(18.0, 18.9, 19.5, 20.0, 20.2, 21.0, 21.2, 20.5, 20.3, 20.0, 18.9, 18.6,18.0, 18.9, 19.5, 20.0, 20.2, 21.0, 21.2, 20.5, 20.3, 20.0, 18.9, 18.6,20.0)),
            AASeriesElement()
                .name("10.12.2020")
                .data(arrayOf(18.5, 18.6, 18.5, 19.0, 19.2, 19.0, 19.2, 19.5, 20.0, 19.8, 19.9, 19.6,18.0, 18.9, 19.5, 20.0, 20.2, 21.0, 21.2, 20.5, 20.3, 20.0, 18.9, 18.6,21.0)),
            AASeriesElement()
                .name("11.12.2020")
                .data(arrayOf(18.2, 18.5, 19.0, 19.5, 19.9, 20.5, 21.0, 20.7, 20.5, 20.2, 19.6, 19.2,18.0, 18.9, 19.5, 20.0, 20.2, 21.0, 21.2, 20.5, 20.3, 20.0, 18.9, 18.6,20.6))
        )
        )


}