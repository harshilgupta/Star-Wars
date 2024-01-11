package com.example.star_wars.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Base Application Class
 * @author by Harshil Gupta
 *
 * This class is the base, the application class for the entire project
 *
 */

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this@MyApp
    }

    companion object {
        private var instance: MyApp? = null
        fun getInstance(): MyApp? {
            if (instance == null) {
                synchronized(MyApp::class.java) {
                    if (instance == null) instance =
                        MyApp()
                }
            }
            return instance
        }
    }
}
