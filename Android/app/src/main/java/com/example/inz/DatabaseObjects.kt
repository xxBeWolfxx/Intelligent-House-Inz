package com.example.inz

import android.content.pm.LauncherApps
import android.util.Log
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

class DatabaseObjects{
    val URL = "http://192.168.0.250:8000/sh"




     fun GetUser(){
         var obj: User = User("valid", "valid","valid","valid", null, null)
         var err: FuelError
         runBlocking {
             ("http://192.168.0.250:8000/sh/userdetail/1/").httpGet().responseObject(User.DeserializerUser()){_, _, result ->
                 when (result){
                     is Result.Failure -> {
                         Log.d("Uwaga", result.getException().toString())
                     }
                     is Result.Success -> {
                         obj = result.component1()!!
                         return@responseObject obj
                     }
                 }
             }
         }



    }


}
//
data class User(var id:String,var name:String, var lastname: String, var email: String, var ESPoutputs: List<ESPO>? = null, var ESPsensor: List<ESPS>? = null){
        class DeserializerUser: ResponseDeserializable<User>{
            override fun deserialize(content: String) = Gson().fromJson(content, User::class.java)
        }

        class  DeserializerUserList: ResponseDeserializable<Array<User>>{
            override fun deserialize(content: String): Array<User>? = Gson().fromJson(content, Array<User>::class.java)
        }
}
data class  ESPO(var name: String, var pin: Int, var status: Boolean, var description: String){
    class DeserializerESP: ResponseDeserializable <ESPO>{
        override fun deserialize(content: String) = Gson().fromJson(content, ESPO::class.java)
    }
}
data class  ESPS(var name: String, var pin: Int, var value: Int, var description: String){}



