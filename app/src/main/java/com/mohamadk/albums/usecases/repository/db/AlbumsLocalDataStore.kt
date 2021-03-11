package com.mohamadk.albums.usecases.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumsLocalDataStore {

    @Query("select * from ItemAlbumModel")
    fun albums():Flow<List<ItemAlbumModel>>

    @Insert
    suspend fun insertAll(items: List<ItemAlbumModel>)

    @Query("SELECT COUNT(id) FROM ItemAlbumModel")
    fun albumsCount(): Int
}