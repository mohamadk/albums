package com.mohamadk.albums.app.di

import com.mohamadk.albums.AlbumsFragment
import com.mohamadk.albums.app.App
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindsAlbumsFragment(): AlbumsFragment

    @ContributesAndroidInjector
    abstract fun bindsApp(): App
}
