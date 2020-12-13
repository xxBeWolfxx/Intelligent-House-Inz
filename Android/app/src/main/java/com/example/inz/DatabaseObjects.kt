package com.example.inz

import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.Serializable

class DatabaseObjects():ViewModel(){
    val URL = "http://192.168.0.250:8000/sh"
    val URLuser = ""

    fun GetESPs(numberESPO: Int, numberESPS: Int, user: User)
    {
        if (numberESPS <= 1)  GetESPS(user)
        else GetListESPS(user)

        if (numberESPO <= 1) GetESPO(user)
        else GetListESPO(user)



    }
    fun GetListESPO(user: User)
    {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main)
                {
                    val (request, response, result) = ("http://192.168.0.250:8000/sh/userdetail/1/").httpGet()
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
                    val (request, response, result) = ("http://192.168.0.250:8000/sh/userdetail/1/").httpGet()
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
                    val (request, response, result) = ("http://192.168.0.250:8000/sh/userdetail/1/").httpGet()
                            .responseObject(ESPO.DeserializerESP())
                    user.ESPoutputs = arrayOf(result.component1()!!)


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
                    val (request, response, result) = ("http://192.168.0.250:8000/sh/userdetail/1/").httpGet()
                            .responseObject(ESPS.DeserializerESP())
                    user.ESPsensor = arrayOf(result.component1()!!)


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



data class User(var id:String,var name:String, var lastname: String, var email: String, var ESPoutputs: Array<ESPO>? = null, var ESPsensor: Array<ESPS>? = null):Serializable {

        class DeserializerUser: ResponseDeserializable<User>{
            override fun deserialize(content: String) = Gson().fromJson(content, User::class.java)
        }

        class  DeserializerUserList: ResponseDeserializable<Array<User>>{
            override fun deserialize(content: String): Array<User>? = Gson().fromJson(content, Array<User>::class.java)
        }
}
data class  ESPO(var name: String, var pin: Int, var status: Boolean, var description: String):Serializable {
    class DeserializerESP: ResponseDeserializable <ESPO>{
        override fun deserialize(content: String) = Gson().fromJson(content, ESPO::class.java)
    }
    class  DeserializerESPList: ResponseDeserializable<Array<ESPO>>{
        override fun deserialize(content: String): Array<ESPO>? = Gson().fromJson(content, Array<ESPO>::class.java)
    }
}
data class  ESPS(var name: String, var pin: Int, var value: Int, var description: String):Serializable {
    class DeserializerESP: ResponseDeserializable <ESPS>{
        override fun deserialize(content: String) = Gson().fromJson(content, ESPS::class.java)
    }
    class  DeserializerESPList: ResponseDeserializable<Array<ESPS>>{
        override fun deserialize(content: String): Array<ESPS>? = Gson().fromJson(content, Array<ESPS>::class.java)
    }
}



