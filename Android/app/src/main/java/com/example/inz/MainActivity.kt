package com.example.inz


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)


        var base = DatabaseObjects()
//        GlobalScope.launch {
//            var obj = base.GetUser()
//        }
        var obj = base.GetUser()






        val btnOpenMainView: Button = findViewById(R.id.button_login)
        btnOpenMainView.setOnClickListener{
            val intent = Intent(this, MainView::class.java)
            startActivity(intent)
        }



    }
}