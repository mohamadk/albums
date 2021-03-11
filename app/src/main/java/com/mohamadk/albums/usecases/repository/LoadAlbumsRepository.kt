package com.mohamadk.albums.usecases.repository

import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow


interface LoadAlbumsRepository {
    val netWorkFailureStateFlow:SharedFlow<NetworkError>
    fun albums(coroutineScope: CoroutineScope): Flow<List<ItemAlbumModel>>
}

class NetworkError(val e:Throwable?=null)
