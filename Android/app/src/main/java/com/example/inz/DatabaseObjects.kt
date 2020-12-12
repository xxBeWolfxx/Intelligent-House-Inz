package com.example.inz

import android.content.Context
import android.content.pm.LauncherApps
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.Serializable
import kotlin.coroutines.suspendCoroutine

class DatabaseObjects():ViewModel(){
    val URL = "http://192.168.0.250:8000/sh"
    val URLuser = ""

    fun GetESPs(numberESPO: Int, numberESPS: Int, window: Context?)
    {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val (request, response, result) = ("http://192.168.0.250:8000/sh/userdetail/1/").httpGet()
                    .responseObject(User.DeserializerUser())
                withContext(Dispatchers.Main)
                {
                    when (result) {
                        is Result.Failure -> {
                            Toast.makeText(window, "CHUJEK", Toast.LENGTH_LONG).show()
                        }
                        is Result.Success -> {

                        }
                    }
                }
            }
            catch (e: Exception)
            {
                withContext(Dispatchers.Main)
                {
                    Toast.makeText(window, "Something went wrong :( Check Internet", Toast.LENGTH_LONG)
                }
            }
        }

    }
    fun GetListESPO()
    {

    }
    fun GetListESPS()
    {

    }
    fun GetESPO()
    {

    }
    fun GetESPS()
    {

    }


}


//
data class User(var id:String,var name:String, var lastname: String, var email: String, var ESPoutputs: List<ESPO>? = null, var ESPsensor: List<ESPS>? = null):Serializable {

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



