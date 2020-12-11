package com.example.inz


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(){


    var  obj2: User = User("valid", "valid", "valid", "valid", null, null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)


        var base = DatabaseObjects()
//        GlobalScope.launch(Dispatchers.IO) {
//            val user = DatabaseObjects().GetUser()
//            withContext(Dispatchers.Main){
//            Log.d("UWAGA", user)
//            }
//
//        }



       // var obj = base.GetUser()





        val btnOpenMainView: Button = findViewById(R.id.button_login)
        btnOpenMainView.setOnClickListener{
//            val intent = Intent(this, MainView::class.java)
//            startActivity(intent)
            GetUserDatabase()
        }



    }


    fun GetUserDatabase() {
        var txt: String = ""
        GlobalScope.launch(Dispatchers.IO) {
            val (request, response, result) = ("http://192.168.0.250:8000/sh/userdetail/1/").httpGet().responseObject(User.DeserializerUser())
                    withContext(Dispatchers.Main)
                    {
                    when (result) {
                        is Result.Failure -> {

                                Log.d("Uwaga", result.getException().toString())
                                obj2.name = "Arek"
                                txt = "CHUJ"
                                Toast.makeText(this@MainActivity, txt, Toast.LENGTH_LONG).show()
                        }
                        is Result.Success -> {
                            obj2 = result.component1()!!
                            txt = "Dupa"

                        }
                    }
                }
        }
    }
}