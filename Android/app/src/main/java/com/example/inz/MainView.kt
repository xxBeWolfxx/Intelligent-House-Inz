package com.example.inz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.mainview.*


class MainView : AppCompatActivity() {
    lateinit var  toggle: ActionBarDrawerToggle

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainview)

        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId)
            {
                R.id.nav_btn1 -> Toast.makeText(applicationContext, "Przycisk 1", Toast.LENGTH_SHORT).show()
                R.id.nav_btn2 -> Toast.makeText(applicationContext, "Przycisk 2", Toast.LENGTH_SHORT).show()

            }
            true
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}