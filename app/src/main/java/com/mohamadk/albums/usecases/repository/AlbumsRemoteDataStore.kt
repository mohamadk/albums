package com.mohamadk.albums.usecases.repository

import com.mohamadk.albums.usecases.AlbumsResponse

interface AlbumsRemoteDataStore {

    suspend fun loadAlbums():AlbumsResponse
}
