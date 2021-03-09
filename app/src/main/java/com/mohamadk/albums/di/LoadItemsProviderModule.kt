package com.mohamadk.albums.di

import android.content.Context
import androidx.room.Room
import com.mohamadk.albums.usecases.repository.db.AlbumsDao
import com.mohamadk.albums.usecases.repository.db.AlbumsDataBase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class LoadItemsProviderModule {

    companion object {
        const val ALBUMS_DATABASE_NAME = "albumsDataBase"
    }

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    fun provideAlbumsDataBase(context: Context): AlbumsDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            AlbumsDataBase::class.java,
            ALBUMS_DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideAlbumsDao(albumsDataBase: AlbumsDataBase): AlbumsDao {
        return albumsDataBase.albumsDao()
    }

}