package com.agence.caol

import android.app.Application
import android.content.Context
import com.agence.caol.viewmodels.moduleListConsultores
import com.agence.caol.viewmodels.moduleMain
import org.koin.android.ext.android.startKoin

class CaolApplication : Application(){

    companion object {
        @get:Synchronized lateinit var instance: CaolApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, listOf(moduleMain, moduleListConsultores))
    }

    fun getContext(): Context {
        return applicationContext
    }
}