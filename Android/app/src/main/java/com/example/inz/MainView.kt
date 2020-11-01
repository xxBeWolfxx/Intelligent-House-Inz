package com.example.inz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.mainview.*


class MainView : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
//    lateinit var  toggle: ActionBarDrawerToggle
    lateinit var homeFragment: HomeFragment
    lateinit var  inputFragment: InputFragment
    lateinit var  OutputFragment: OutputFragment
    lateinit var  settingsFragment: SettingsFragment

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainview)
//
//        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
//        drawer_layout.addDrawerListener(toggle)
//        toggle.syncState()
        setSupportActionBar(toolBar)
        val actionBar = supportActionBar
        actionBar?.title = "Navigation Drawer"
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle (
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
        homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()

        nav_view.setNavigationItemSelectedListener(this)

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
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
            }
            R.id.nav_btn2 -> {
                inputFragment = InputFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, inputFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}