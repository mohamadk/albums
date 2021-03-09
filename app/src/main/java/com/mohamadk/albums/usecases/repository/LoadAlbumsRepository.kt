package com.mohamadk.albums.usecases.repository

import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import kotlinx.coroutines.flow.Flow


interface LoadAlbumsRepository {

    suspend fun loadAlbums()

    fun albums(): Flow<List<ItemAlbumModel>>

    suspend fun albumsCount(): Int
}
