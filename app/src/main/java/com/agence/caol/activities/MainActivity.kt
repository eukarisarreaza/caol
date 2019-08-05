package com.agence.caol.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.lifecycle.Observer
import com.agence.caol.R
import com.agence.caol.fragments.PorConsultorFragment
import com.agence.caol.models.database.AppDataBase
import com.agence.caol.worker.DatabaseWorker

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val TAG by lazy { DatabaseWorker::class.java.simpleName }
    private val database by lazy { AppDataBase.getInstance(applicationContext) }

    companion object{
        fun newInstance(context: Context): Intent {
            val intent= Intent(context, MainActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()

    }

    private fun setupView() {

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)


        var frag = PorConsultorFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, frag, "FM_MAIN")
            .commit()
    }

    private fun getUser(){

        database.caoUsuarioDao().getAll().observe(this, Observer {
            if(it!=null){
                Log.e(TAG, "LISTA de usuarios en maiiinnnnn ${it.size}")
            }
        })
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_agence -> {

            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
