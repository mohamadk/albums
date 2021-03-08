package com.mohamadk.app

import android.app.Application
import com.mohamadk.app.di.AlbumsApp

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AlbumsApp.init(this)
    }

}