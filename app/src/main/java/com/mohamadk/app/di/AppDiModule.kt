package com.mohamadk.app.di

import android.content.Context
import com.mohamadk.app.App
import dagger.Module
import dagger.Provides

@Module
class AppDiModule {

    @Provides
    fun provideContext(app: App): Context {
        return app.applicationContext
    }

}