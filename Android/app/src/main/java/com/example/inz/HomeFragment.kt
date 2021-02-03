package com.example.inz


import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.sql.Date


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var view_city: TextView? = null
    var view_temp: TextView? = null
    var view_press: TextView? = null
    var view_hum: TextView? = null
    var City:String?=null
    var view_weather: ImageView? = null
    var view_sunset: TextView? = null
    var view_sunrise: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_weather = view.findViewById(R.id.wheather_image)
        view_city = view.findViewById(R.id.town)
        view_temp = view.findViewById(R.id.temp)
        view_press = view.findViewById(R.id.press)
        view_hum = view.findViewById(R.id.hum)
        view_sunrise=view.findViewById(R.id.sunrise_time)
        view_sunset=view.findViewById(R.id.sunset_time)


        button_temp.setOnClickListener {
            findMainSensor(MyApplicaton.User!!, "Temperature")
            val intent = activity?.supportFragmentManager?.beginTransaction()
            intent?.replace(R.id.frame_layout, ChartFragment())
            intent?.disallowAddToBackStack()
            intent?.commit()
        }
        City="Poznan"; // *********************************** CHANGE THE CITY
        api_key("https://api.openweathermap.org/data/2.5/weather?q=$City&appid=a6f41d947e0542a26580bcd5c3fb90ef&units=metric")
    }

    ////////////// GET THE WEATHER DATA FROM OPENWEATHER [PAGE] //////////////

    fun api_key(Key: String) {
        val client = OkHttpClient()


        val request = Request.Builder()
            .url(Key)
            .get()
            .build()
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            val response = client.newCall(request).execute()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }


                @SuppressLint("SimpleDateFormat")
                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body!!.string()
                    try {
                        val json = JSONObject(responseData)
                        val array = json.getJSONArray("weather")
                        val `object` = array.getJSONObject(0)
                        val icons = `object`.getString("icon")
                        val temp1 = json.getJSONObject("main")
                        val Temperature = temp1.getDouble("temp")
                        val pressure = temp1.getDouble("pressure")
                        val humidity = temp1.getDouble("humidity")
                        val Town = json.getString("name")
                        val sys1: JSONObject = json.getJSONObject("sys")
                        val rise: Long = sys1.getLong("sunrise")
                        val set: Long = sys1.getLong("sunset")


                        val javaTimestamp1 = rise * 1000L
                        val date1 = Date(javaTimestamp1)
                        val sunrise: String = SimpleDateFormat("kk:mm").format(date1)

                        val javaTimestamp2 = set * 1000L
                        val date2 = Date(javaTimestamp2)
                        val sunset: String = SimpleDateFormat("kk:mm").format(date2)

                        view_sunrise?.let {

                                setText(it, sunrise)

                            }

                        view_sunset?.let {

                            setText(it, sunset)

                        }

                        view_city?.let {
                            if (Town != null) {
                                setText(it, Town)

                            }
                        }

                        val temps =
                            Math.round(Temperature).toString() + "°C"
                        view_temp?.let { setText(it, temps) }
                        val press = "Pressure: "+
                                Math.round(pressure).toString() + " hPa"
                        view_press?.let { setText(it, press) }
                        val humid ="Humidity: "+
                                Math.round(humidity).toString() + " %"
                        view_hum?.let { setText(it, humid) }
                        view_weather?.let { setImage(it, icons) }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun setText(text: TextView, value: String) {
        this.activity?.runOnUiThread(Runnable { text.text = value })
    }

    fun setImage(
        imageView: ImageView,
        value: String
    ) {
        this.activity?.runOnUiThread(Runnable { //paste switch
            when (value) {
                "01d" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d01d))
                "01n" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d01d))
                "02d" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d02d))
                "02n" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d02d))
                "03d" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d03d))
                "03n" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d03d))
                "04d" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d04d))
                "04n" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d04d))
                "09d" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d09d))
                "09n" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d09d))
                "10d" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d10d))
                "10n" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d10d))
                "11d" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d11d))
                "11n" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d11d))
                "13d" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d13d))
                "13n" -> imageView.setImageDrawable(resources.getDrawable(R.drawable.d13d))
                else -> imageView.setImageDrawable(resources.getDrawable(R.drawable.wheather))
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        //DatabaseObjects().GetESPs(1,1, getActivity())

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun findMainSensor(user: User, sensor: String) {
        val temp = user.ESPsensor?.find { it.name == sensor }
        MyApplicaton.listValue = temp!!.valueAvgDay.split(",")
    }
}