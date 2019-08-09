package com.agence.caol.activities

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.Observer
import com.agence.caol.R
import com.agence.caol.models.database.AppDataBase
import com.agence.caol.utilities.KEY_SET_DATA
import com.agence.caol.worker.DatabaseWorker
import com.cplus.android.utilities.Utils

class SplashActivity : AppCompatActivity() {
    private val TAG by lazy { DatabaseWorker::class.java.simpleName }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        /********    PARA ASEGURAR QUE EN EL MAIN YA ESTE CARGADA TODA LA DATA EN LOCAL       ********/
        val database= AppDataBase.getInstance(applicationContext)
        database.caoUsuarioDao().getAll().observe(this, Observer {  })

        timerData()

    }


    fun timerData(){
        Handler().postDelayed({
            Log.e(TAG, "METODO POSR DELAY")
            if(Utils.getPreferences(applicationContext, KEY_SET_DATA,   false, "b") as Boolean){
                var main= MainActivity.newInstance(this)
                main.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(main)
            }else{
                timerData()
            }

        }, 3000)

    }
}
