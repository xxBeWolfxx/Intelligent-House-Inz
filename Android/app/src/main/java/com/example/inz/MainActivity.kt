package com.example.inz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

<<<<<<< HEAD
        // editTextTextLogin.setColor(Color.WHITE);
        //editTextTextPassword.setColor(Color.WHITE);
=======
        val btnOpenMainView: Button = findViewById(R.id.button_login)
        btnOpenMainView.setOnClickListener{
            val intent = Intent(this, MainView::class.java)
            startActivity(intent)
        }

>>>>>>> 66ba46f6347237a32ce941ec6cc9b16b7efa61ce

    }
}