package com.example.inz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.mainview.*
import kotlinx.coroutines.*


class MainView : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
//    lateinit var  toggle: ActionBarDrawerToggle
    lateinit var homeFragment: HomeFragment
    lateinit var  inputFragment: InputFragment
    lateinit var  outputFragment: OutputFragment
    lateinit var  settingsFragment: SettingsFragment
    lateinit var  actionBar: ActionBar
    lateinit var user: User


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainview)
        user = intent.getSerializableExtra("User") as User





//
//        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
//        drawer_layout.addDrawerListener(toggle)
//        toggle.syncState()

        setSupportActionBar(toolBar)
        actionBar = supportActionBar!!
        actionBar?.title = "Home"
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolBar,
            (R.string.open),
            (R.string.close)
        ){

        }
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        inputFragment = InputFragment()
        outputFragment =   OutputFragment()
        settingsFragment = SettingsFragment()
        homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment).setTransition(
            FragmentTransaction.TRANSIT_FRAGMENT_OPEN
        ).commit()

        nav_view.setNavigationItemSelectedListener(this)

        //DatabaseObjects().GetESPs(1,1,this)



    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(toggle.onOptionsItemSelected(item))
//        {
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }


    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        when(item.itemId){
            R.id.nav_btn1 -> {
                homeFragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment)
                    .setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_OPEN
                    ).commit()
                actionBar?.title = "Home"
            }
            R.id.nav_btn2 -> {
                inputFragment = InputFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, inputFragment)
                    .setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_OPEN
                    ).commit()
                actionBar?.title = "Sensors"

            }
            R.id.nav_btn3 -> {
                outputFragment = OutputFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, outputFragment)
                    .setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_OPEN
                    ).commit()
                actionBar?.title = "Devices"

            }
            R.id.nav_btn4 -> {
                settingsFragment = SettingsFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.frame_layout,
                    settingsFragment
                ).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                actionBar?.title = "Settings"

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }





}

