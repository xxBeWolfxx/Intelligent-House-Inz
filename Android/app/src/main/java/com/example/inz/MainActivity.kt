package com.example.inz


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity() {


    lateinit var progressView: ConstraintLayout
    lateinit var progressText: TextView


    var obj2: User = User("valid", "valid", "valid", "valid", null, null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)



        val btnOpenMainView: Button = findViewById(R.id.button_login)
        btnOpenMainView.setOnClickListener {
            progressView = findViewById(R.id.progress_window)
            progressText = findViewById(R.id.progressText)
            progressView.isVisible = true
            progressText.text = "Loading"
            GetUserDatabase()
        }


    }


    fun GetUserDatabase() {
        var txt: String
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val (request, response, result) = ("http://192.168.0.250:8000/sh/userdetail/1/").httpGet()
                    .responseObject(User.DeserializerUser())
                withContext(Dispatchers.Main)
                {
                    when (result) {
                        is Result.Failure -> {
                            Log.d("Uwaga", result.getException().toString())
                            progressText.text = "Wrong login or password"
                            delay(800L)
                            obj2.name = "Arek"
                            val intent = Intent(this@MainActivity, MainView::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is Result.Success -> {
                            obj2 = result.component1()!!
                            progressText.text = "Success!"
                            delay(800L)
                            txt = "Dupa"
                            val intent = Intent(this@MainActivity, MainView::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
            catch (e: Exception)
            {
                withContext(Dispatchers.Main)
                {
                    Toast.makeText(this@MainActivity, "Something went wrong :( Check Internet", Toast.LENGTH_LONG)
                }
            }
        }
    }
}