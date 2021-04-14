package com.uzlov.moviefind.application

import android.app.Application
import androidx.room.Room
import com.uzlov.moviefind.database.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private const val db_name = "FilmsDatabase"

        val getFilmsDatabase by lazy {
            Room.databaseBuilder(
                appInstance!!.applicationContext,
                AppDatabase::class.java,
                db_name
            )
            .allowMainThreadQueries()
            .build()
        }
    }
}