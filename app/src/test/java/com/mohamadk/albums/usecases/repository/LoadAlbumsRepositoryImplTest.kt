package com.mohamadk.albums.usecases.repository

import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import com.mohamadk.albums.utils.CoroutineTestExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.time.ExperimentalTime

internal class LoadAlbumsRepositoryImplTest {

    @ExperimentalCoroutinesApi
    @RegisterExtension
    @JvmField
    val coroutineTestExtension = CoroutineTestExtension()

    private val itemAlbumModel = ItemAlbumModel(1, "title")
    private val itemAlbumModels = listOf(itemAlbumModel)
    private val itemAlbumModelsFlow = MutableStateFlow(itemAlbumModels)
    val error = IllegalStateException("something went wrong :O")

    @ExperimentalCoroutinesApi
    @Test
    fun `run success`() = coroutineTestExtension.testDispatcher.runBlockingTest {
        val localAvailableAlbumsCount = 0
        LoadAlbumsRepositoryImpRobo(
            coroutineTestExtension.testDispatcher,
            itemAlbumModelsFlow,
            localAvailableAlbumsCount,
            error,
            itemAlbumModels
        )
            .albums(this)
            .verifyLoadFromRemoteDataStore()
            .verifyInsertItemsToLocalDataStore(itemAlbumModels)
    }

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @Test
    fun `run failure no Items available in local`() =
        coroutineTestExtension.testDispatcher.runBlockingTest {
            val localAvailableAlbumsCount = 0

            LoadAlbumsRepositoryImpRobo(
                coroutineTestExtension.testDispatcher,
                itemAlbumModelsLocalFlow = itemAlbumModelsFlow,
                albumsCount = localAvailableAlbumsCount,
                error = error
            )
                .albums(this)
                .verifyLoadFromRemoteDataStore()
                .verifyInsertItemsToLocalDataStore()
            // https://github.com/Kotlin/kotlinx.coroutines/issues/1204
//            .verifyNetworkError(NetworkError(error))
        }



    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @Test
    fun `run failure Items available in local`() =
        coroutineTestExtension.testDispatcher.runBlockingTest {
            val localAvailableAlbumsCount = 5

            LoadAlbumsRepositoryImpRobo(
                coroutineTestExtension.testDispatcher,
                itemAlbumModelsLocalFlow = itemAlbumModelsFlow,
                albumsCount = localAvailableAlbumsCount,
                error = error
            )
                .albums(this)
                .verifyLoadFromRemoteDataStore()
                .verifyInsertItemsToLocalDataStore()
            // https://github.com/Kotlin/kotlinx.coroutines/issues/1204
//            .verifyNetworkError(NetworkError(error))
        }
}
