package com.mohamadk.albums.usecases.repository

import com.mohamadk.albums.usecases.repository.db.AlbumsDao
import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadAlbumsRepositoryImpl @Inject constructor(
    private val albumsDao: AlbumsDao,
    private val albumsRemoteDataStore: AlbumsRemoteDataStore
) : LoadAlbumsRepository {

    override suspend fun loadAlbums() {
        albumsDao.insertAll(albumsRemoteDataStore.loadAlbums())
    }

    override fun albums(): Flow<List<ItemAlbumModel>> {
        return albumsDao.albums()
    }

    override suspend fun albumsCount(): Int {
        return albumsDao.albumsCount()
    }
}