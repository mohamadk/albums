package com.mohamadk.albums.usecases.repository

import com.mohamadk.albums.usecases.AlbumsResponse
import javax.inject.Inject

class AlbumsRemoteDataStoreImpl @Inject constructor() : AlbumsRemoteDataStore {
    override suspend fun loadAlbums(): AlbumsResponse {
        return AlbumsResponse(listOf())
    }
}