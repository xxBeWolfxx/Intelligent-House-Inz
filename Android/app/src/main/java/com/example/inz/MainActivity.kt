package com.example.inz


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity() {


    lateinit var progressView: ConstraintLayout
    lateinit var progressText: TextView
    lateinit var logo: ImageView
    lateinit var inputView: CardView
    lateinit var buttonView: CardView


    var user: User = User("valid", "valid", "valid", "valid", null, null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        val logo_animation: Animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        val bottomAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        logo = findViewById(R.id.logo)
        inputView = findViewById(R.id.cardView2)
        buttonView = findViewById(R.id.cardView)
        logo.startAnimation(logo_animation)
        inputView.startAnimation(bottomAnimation)
        buttonView.startAnimation(bottomAnimation)



        val btnOpenMainView: Button = findViewById(R.id.button_login)
        btnOpenMainView.setOnClickListener {
            progressView = findViewById(R.id.progress_window)
            progressText = findViewById(R.id.progressText)
            progressView.visibility = View.VISIBLE
            progressText.text = "Loading..."
            GetUserDatabase()
        }


    }


    fun GetUserDatabase() {
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
                            delay(100L)
                            user.name = "Arek"
                            user.lastname = "Krusz"
                            user.email = "aaa@aaa.com"
                            val intent = Intent(this@MainActivity, MainView::class.java)
                            intent.putExtra("User", user)
                            startActivity(intent)
                            finish()
                        }
                        is Result.Success -> {
                            user = result.component1()!!
                            progressText.text = "Success!"
                            delay(100L)
                            val intent = Intent(this@MainActivity, MainView::class.java)
                            intent.putExtra("User", user)
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