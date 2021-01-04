package com.example.inz

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.Serializable

class DatabaseObjects():ViewModel(){
    private val URL = "http://192.168.0.250:8000/sh"
    val URLuser = "$URL/userlogin/"
    private val URLuserESPS = "$URL/userespsensor/"
    private val URLuserESPO = "$URL/userespout/"

    fun CreateArrayESP(user: User, mode: Boolean): ArrayList<ItemCardView>
    {
        val list = ArrayList<ItemCardView>()

        if (mode)                                   //ESPO
        {
            for (i in user.ESPoutputs!!)
            {
                val item = ItemCardView(R.drawable.home, i.name, i.status.toString(),i.id.toInt())
                list += item
            }
        }
        else                                        //ESPS
        {
            for (i in user.ESPsensor!!)
            {
                val item = ItemCardView(R.drawable.humidity, i.name, i.valueTemp.toString(),i.id.toInt())
                list += item
            }

        }



        return list
    }

    fun GetESPs(numberESPO: Int, numberESPS: Int, user: User)
    {
        if (numberESPS <= 1)
        {
            GetESPS(user)
        }
        else
        {
            GetListESPS(user)
        }

        if (numberESPO <= 1)
        {
            GetESPO(user)
        }
        else
        {
            GetListESPO(user)
        }
    }
    fun GetListESPO(user: User)
    {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main)
                {
                    val (request, response, result) = ("$URLuserESPO${user.id}").httpGet()
                            .responseObject(ESPO.DeserializerESPList())
                    user.ESPoutputs = result.component1()!!

                }
            }
            catch (e: Exception)
            {
                withContext(Dispatchers.Main)
                {

                }
            }
        }
    }
    fun GetListESPS(user: User)
    {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main)
                {
                    val (request, response, result) = ("http://192.168.0.250:8000/sh/userespsensor/1/").httpGet()
                            .responseObject(ESPS.DeserializerESPList())
                    user.ESPsensor = result.component1()!!

                }
            }
            catch (e: Exception)
            {
                withContext(Dispatchers.Main)
                {

                }
            }
        }
    }
    fun GetESPO(user: User)
    {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main)
                {
                    "$URLuserESPO${user.id}/?format=json".httpGet().responseObject(ESPO.DeserializerESPList()){request, response, resultO ->
                        user.ESPoutputs = resultO.get()
                    }


                }
            }
            catch (e: Exception)
            {
                withContext(Dispatchers.Main)
                {

                }
            }
        }
    }
    fun GetESPS(user: User)
    {
        GlobalScope.launch(Dispatchers.IO) {
            try {

                withContext(Dispatchers.Main)
                {
                   "http://192.168.0.250:8000/sh/userespsensor/1/".httpGet().responseObject(ESPS.DeserializerESPList()) {request, response, resultS ->
                        user.ESPsensor = resultS.get()
                    }
                }
            }

            catch (e: Exception)
            {
                withContext(Dispatchers.Main)
                {

                }
            }
        }
    }


}



data class User(var id:String,var name:String, var lastname: String, var email: String, var login: String, var password: String, var ESPoCount: Int, var ESPsCount:Int, var ESPoutputs: Array<ESPO>? = null, var ESPsensor: Array<ESPS>? = null):Serializable {

        class DeserializerUser: ResponseDeserializable<User>{
            override fun deserialize(content: String) = Gson().fromJson(content, User::class.java)
        }

        class  DeserializerUserList: ResponseDeserializable<Array<User>>{
            override fun deserialize(content: String): Array<User>? = Gson().fromJson(content, Array<User>::class.java)
        }
}
data class  ESPO(var id: String, var name: String, var pin: Int, var status: Boolean, var description: String):Serializable {
    class DeserializerESP: ResponseDeserializable<ESPO>{
        override fun deserialize(content: String) = Gson().fromJson(content, ESPO::class.java)
    }
    class  DeserializerESPList: ResponseDeserializable<Array<ESPO>>{
        override fun deserialize(content: String): Array<ESPO>? = Gson().fromJson(content, Array<ESPO>::class.java)
    }
}
data class  ESPS(var id: String, var name: String, var pin: Int, var valueTemp: Int, var valueAvgDay: String, var valueAvgWeek: String, var description: String):Serializable {
    class DeserializerESP: ResponseDeserializable<ESPS>{
        override fun deserialize(content: String) = Gson().fromJson(content, ESPS::class.java)
    }
    class  DeserializerESPList: ResponseDeserializable<Array<ESPS>>{
        override fun deserialize(content: String): Array<ESPS>? = Gson().fromJson(content, Array<ESPS>::class.java)
    }
}




