package com.mohamadk.albums.di

import com.mohamadk.albums.usecases.repository.AlbumsRemoteDataStore
import com.mohamadk.albums.usecases.repository.AlbumsRemoteDataStoreImpl
import com.mohamadk.albums.usecases.repository.LoadAlbumsRepository
import com.mohamadk.albums.usecases.repository.LoadAlbumsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module(includes = [LoadItemsProviderModule::class])
abstract class LoadAlbumsModule {

    @Binds
    abstract fun loadAlbumsRepository(loadAlbumsRepositoryImpl: LoadAlbumsRepositoryImpl): LoadAlbumsRepository

    @Binds
    abstract fun loadAlbumsRemoteDataStoreImpl(albumsRemoteDataStoreImpl: AlbumsRemoteDataStoreImpl): AlbumsRemoteDataStore
}