package com.estiven.cryptodivisas

import android.app.Application
import androidx.room.Room
import com.estiven.cryptodivisas.repository.CurrenciesDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CurrenciesApplication : Application() {

    companion object {
        lateinit var database: CurrenciesDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room
            .databaseBuilder(this, CurrenciesDatabase::class.java, "currencies")
            .build()
    }
}