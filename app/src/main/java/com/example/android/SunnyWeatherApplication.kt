package com.example.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN ="LBCnK80RZUoBW3uM"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}