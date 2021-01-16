package com.example.inz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
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
    lateinit var  actionBar: ActionBar
    lateinit var user: User


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainview)

        user = intent.getSerializableExtra("User") as User
        SetHeader(user, nav_view.getHeaderView(0))
        MyApplicaton.User = user

        GlobalScope.launch(Dispatchers.IO)
        {
            DatabaseObjects().GetESPs(user.ESPoCount, user.ESPsCount, user)
        }


        setSupportActionBar(toolBar)
        actionBar = supportActionBar!!
        actionBar.title = "Home"
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

        inputFragment = InputFragment()
        outputFragment =   OutputFragment()
        homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment).setTransition(
            FragmentTransaction.TRANSIT_FRAGMENT_OPEN
        ).commit()

        nav_view.setNavigationItemSelectedListener(this)

        RefreshButton.setOnClickListener {
            finish()
            startActivity(intent)
        }

        //DatabaseObjects().GetESPs(1,1,this)

    }

    @SuppressLint("ShowToast")
    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        when(item.itemId){
            R.id.nav_btn1 -> {
                homeFragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment)
                    .setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_OPEN
                    ).commit()
                actionBar.title = "Home"

            }
            R.id.nav_btn2 -> {
                inputFragment = InputFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, inputFragment)
                    .setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_OPEN
                    ).commit()
                actionBar.title = "Sensors"

            }
            R.id.nav_btn3 -> {
                outputFragment = OutputFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, outputFragment)
                    .setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_OPEN
                    ).commit()
                actionBar.title = "Devices"

            }
            R.id.nav_btn6 -> {

                GlobalScope.launch() {
                    val intent = Intent(this@MainView, MainActivity::class.java)
                    delay(700)
                    startActivity(intent)
                    finish()

                }

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun SetHeader(user: User, headerView: View)
    {
        val header_name = headerView.findViewById<TextView>(R.id.header_name)
        val header_email = headerView.findViewById<TextView>(R.id.header_email)
        header_email.text = user.email
        header_name.text = user.name
    }





}

