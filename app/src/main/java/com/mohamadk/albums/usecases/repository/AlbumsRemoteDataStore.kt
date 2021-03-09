package com.mohamadk.albums.usecases.repository

import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import retrofit2.http.GET

interface AlbumsRemoteDataStore {

    @GET("/albums")
    suspend fun loadAlbums(): List<ItemAlbumModel>
}
