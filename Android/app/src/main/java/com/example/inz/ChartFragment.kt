package com.example.inz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAScrollablePlotArea
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import com.github.aachartmodel.aainfographics.aatools.AAGradientColor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    val aaChartModel : AAChartModel = AAChartModel()
        .chartType(AAChartType.Area)
        .title("Temperature")
        .titleStyle(AAStyle()
            .color("#FFFFFF")
        )
        .dataLabelsStyle(AAStyle()
            .color("#FFFFFF")
        )
        .zoomType(AAChartZoomType.X)

 //       .backgroundColor("#D6D6D6")
        .backgroundColor(AAGradientColor.linearGradient("#1F1139","#041F39"))
        .markerSymbol(AAChartSymbolType.Circle)
        .colorsTheme(arrayOf("#700c28", "#5814D3", "#06caf4")
        )
        .yAxisGridLineWidth(0.00f)
        .yAxisLabelsEnabled(false)
        .yAxisTitle("")
        .xAxisTickInterval(1)
        .xAxisLabelsEnabled(true)
        .axesTextColor("#FFFFFF")
        .categories(arrayOf("00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"))
        .dataLabelsEnabled(true)
        .yAxisMin(17.00f)
//        .scrollablePlotArea( AAScrollablePlotArea()
//            .minWidth(3000)
//            .scrollPositionX(1f)
//        )
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aaChartView = view?.findViewById<AAChartView>(R.id.aa_chart_view)
        aaChartView?.aa_drawChartWithChartModel(aaChartModel)



    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}