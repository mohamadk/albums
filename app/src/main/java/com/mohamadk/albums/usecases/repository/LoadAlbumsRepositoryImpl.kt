package com.mohamadk.albums.usecases.repository

import com.mohamadk.albums.usecases.repository.db.AlbumsLocalDataStore
import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoadAlbumsRepositoryImpl @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val albumsDao: AlbumsLocalDataStore,
    private val albumsRemoteDataStore: AlbumsRemoteDataStore
) : LoadAlbumsRepository {

    private val _netWorkFailureStateFlow = MutableSharedFlow<NetworkError>()
    override val netWorkFailureStateFlow: SharedFlow<NetworkError> = _netWorkFailureStateFlow

    override fun albums(coroutineScope: CoroutineScope): Flow<List<ItemAlbumModel>> {
        coroutineScope.launch(coroutineDispatcher) {
            val result = albumsRemoteDataStore.runCatching {
                albumsDao.insertAll(loadAlbums())
            }
            if (result.isFailure && albumsDao.albumsCount() == 0) {
                _netWorkFailureStateFlow.emit(NetworkError(result.exceptionOrNull()))
            }
        }

        return albumsDao.albums()
    }
}