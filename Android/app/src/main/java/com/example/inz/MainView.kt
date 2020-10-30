package com.example.inz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.widget.Toast

class MainView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainview)
        val nav_view = findViewById<NavigationView>(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener { menuItem ->
            if(menuItem.itemId == R.id.nav_btn1){
                Toast.makeText(applicationContext, "Przycisk 1", Toast.LENGTH_SHORT).show()
            }
            true
        }

    }
}