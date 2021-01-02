package com.example.inz


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {


    lateinit var logo: ImageView
    lateinit var inputView: CardView
    lateinit var buttonView: CardView
    lateinit var bodyDialog: TextView


    var user: User = User("valid", "valid", "valid", "valid", null, null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        //Setting animation
        val logo_animation: Animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        val bottomAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        //Setting dialog window with progressBar
        val dialog: Dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bodyDialog = dialog.findViewById(R.id.progressText)


        logo = findViewById(R.id.logo)
        inputView = findViewById(R.id.cardView2)
        buttonView = findViewById(R.id.cardView)
        logo.startAnimation(logo_animation)
        inputView.startAnimation(bottomAnimation)
        buttonView.startAnimation(bottomAnimation)



        val btnOpenMainView: Button = findViewById(R.id.button_login)
        btnOpenMainView.setOnClickListener {
            dialog.show()
            bodyDialog.text = "Loading..."
            GetUserDatabase(dialog)
        }


    }


    fun GetUserDatabase(dialog: Dialog) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val (request, response, result) = ("http://192.168.0.250:8000/sh/userdetail/1/").httpGet()
                    .responseObject(User.DeserializerUser())
                withContext(Dispatchers.Main)
                {
                    when (result) {
                        is Result.Failure -> {
                            Log.d("Uwaga", result.getException().toString())
                            bodyDialog.text = "Wrong login or password"
                            delay(100L)
                            user.name = "Arek"
                            user.lastname = "Krusz"
                            user.email = "aaa@aaa.com"
                            val intent = Intent(this@MainActivity, MainView::class.java)
                            intent.putExtra("User", user)
                            delay(300L)
                            dialog.dismiss()
                            startActivity(intent)
                            //overridePendingTransition(R.anim.anim_activity_to_right, R.anim.anim_activity_to_left) NIE DZIALA
                            finish()
                        }
                        is Result.Success -> {
                            user = result.component1()!!
                            bodyDialog.text = "Success!"
                            delay(100L)
                            val intent = Intent(this@MainActivity, MainView::class.java)
                            intent.putExtra("User", user)
                            delay(300L)
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