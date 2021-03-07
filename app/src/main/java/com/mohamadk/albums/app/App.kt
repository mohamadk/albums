package com.mohamadk.albums.app

import android.app.Application
import com.mohamadk.albums.app.di.AlbumsApp

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AlbumsApp.init(this)
    }

}