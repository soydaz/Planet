package com.example.planet

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.planet.core.storage.local.room.PlanetsRoom

class App : Application() {

    init {
        mContext = this
    }

    companion object {

        private lateinit var mContext: App
        private lateinit var mRoom: PlanetsRoom

        @JvmStatic
        fun getContext(): Context {
            return mContext.applicationContext
        }

        @JvmStatic
        fun getRoomInstance() = mRoom

    }


    override fun onCreate() {
        super.onCreate()

        mRoom = PlanetsRoom.getInstance(this)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}