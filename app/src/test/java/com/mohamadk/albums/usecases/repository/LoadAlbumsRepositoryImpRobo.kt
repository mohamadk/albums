package com.mohamadk.albums.usecases.repository

import com.mohamadk.albums.usecases.repository.db.AlbumsLocalDataStore
import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.Assertions.assertEquals

class LoadAlbumsRepositoryImpRobo(
    coroutineDispatcher: CoroutineDispatcher,
    itemAlbumModelsLocalFlow: MutableStateFlow<List<ItemAlbumModel>>,
    albumsCount: Int = 0,
    error: Exception? = null,
    vararg itemAlbumModelsRemotes: List<ItemAlbumModel>
) {

    private val albumsLocalDataStore: AlbumsLocalDataStore = mock()
    private val albumsRemoteDataStore: AlbumsRemoteDataStore = mock()
    private val repositoryImpl: LoadAlbumsRepositoryImpl

    init {
        whenever(albumsLocalDataStore.albums()).thenReturn(itemAlbumModelsLocalFlow)
        albumsRemoteDataStore.stub {
            val stubbing = onBlocking { loadAlbums() }
            when {
                itemAlbumModelsRemotes.isEmpty() -> {
                    stubbing.thenThrow(error)
                    whenever(albumsLocalDataStore.albumsCount()) doReturn albumsCount
                }
                itemAlbumModelsRemotes.size == 1 -> {
                    stubbing doReturn itemAlbumModelsRemotes[0]
                }
                else -> {
                    throw IllegalStateException("wrong test arguments itemAlbumModelsRemotes size shouldn't be more than 1")
                }
            }
        }
        repositoryImpl = LoadAlbumsRepositoryImpl(
                coroutineDispatcher,
                albumsLocalDataStore,
                albumsRemoteDataStore
            )
    }

    fun albums(coroutineScope: CoroutineScope): LoadAlbumsRepositoryImpRobo {
        repositoryImpl.albums(coroutineScope)

        return this
    }

    fun verifyLoadFromRemoteDataStore(): LoadAlbumsRepositoryImpRobo {
        verifyBlocking(albumsRemoteDataStore) {
            loadAlbums()
            times(1)
        }

        return this
    }

    fun verifyInsertItemsToLocalDataStore(vararg itemAlbumModels: List<ItemAlbumModel>): LoadAlbumsRepositoryImpRobo {
        val argumentCaptor = argumentCaptor<List<ItemAlbumModel>>()
        if (itemAlbumModels.isNotEmpty()) {
            verifyBlocking(albumsLocalDataStore) {
                times(itemAlbumModels.size)
                insertAll(argumentCaptor.capture())
            }
        }
        assertEquals(itemAlbumModels.toList(), argumentCaptor.allValues)

        return this
    }

//    suspend fun verifyNetworkError(networkError: NetworkError?): LoadAlbumsRepositoryImpRobo {
//        val networkErrorResult = loadAlbumsRepositoryImpl.netWorkFailureStateFlow.firstOrNull()
//        assertEquals(networkError,networkErrorResult)
//
//        return this
//    }

}