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
import android.widget.*
import androidx.cardview.widget.CardView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {


    lateinit var logo: ImageView
    lateinit var inputView: CardView
    lateinit var buttonView: CardView
    lateinit var bodyDialog: TextView


    var user: User = User("Arek", "Krusz", "valid@valid.pl", "valid", "valid", "null", 0, 0, null, null)
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
            val login: String = findViewById<EditText>(R.id.editTextTextLogin).text.toString()
            val password: String = findViewById<EditText>(R.id.editTextTextPassword).text.toString()
            dialog.show()
            bodyDialog.text = "Loading..."
            GetUserDatabase(dialog, login, password)
        }


    }


    fun GetUserDatabase(dialog: Dialog, login: String, password: String) {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val (request, response, result) = ("${DatabaseObjects().URLuser}${login}/?format=json").httpGet().authenticate(login,password)
                    .responseObject(User.DeserializerUser())
                withContext(Dispatchers.Main)
                {

                    when (result) {
                        is Result.Failure -> {
                            bodyDialog.text = "Wrong login or password"
                            //
                            delay(900L)
                            //
                            val intent = Intent(this@MainActivity, MainView::class.java)
                            intent.putExtra("User", user)
                            delay(700L)
                            dialog.dismiss()
                            startActivity(intent)
                            finish()
                            
                        }
                        is Result.Success -> {
                            val userTemp = result.component1()!!
                            if (password == userTemp.password)
                            {
                                user = userTemp
                                bodyDialog.text = "Success!"
                                delay(100L)
                                val intent = Intent(this@MainActivity, MainView::class.java)
                                intent.putExtra("User", user)
                                delay(700L)
                                dialog.dismiss()
                                startActivity(intent)
                                finish()
                            }
                            else
                            {
                                bodyDialog.text = "Wrong password!"
                                delay(700L)
                                dialog.dismiss()
                            }
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