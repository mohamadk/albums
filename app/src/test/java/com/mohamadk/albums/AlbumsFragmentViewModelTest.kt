package com.mohamadk.albums

import com.mohamadk.albums.adapter.AlbumsListingItemTypes
import com.mohamadk.albums.adapter.AlbumsModelWrapper
import com.mohamadk.albums.adapter.listing.ItemAlbumUiModel
import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import com.mohamadk.albums.utils.CoroutineTestExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension


class AlbumsFragmentViewModelTest {

    @ExperimentalCoroutinesApi
    @RegisterExtension
    @JvmField
    val coroutineTestExtension = CoroutineTestExtension()

    private val itemAlbumModel = ItemAlbumModel(1, "title")
    private val itemAlbumModels = listOf(itemAlbumModel)
    private val itemAlbumUiModel = ItemAlbumUiModel(1, "title")
    private val albumsModelWrappers =
        listOf(AlbumsModelWrapper(AlbumsListingItemTypes.SingleTitleItemType, itemAlbumUiModel))

    private val loading = ViewState(showLoading = true, items = null)
    private val success =
        ViewState(showLoading = false, items = albumsModelWrappers)
    private val failure = ViewState(showLoading = false, items = null)


    @ExperimentalCoroutinesApi
    @Test
    fun `load Albums success`() = coroutineTestExtension.testDispatcher.runBlockingTest {
        AlbumsFragmentViewModelRobo(
            this,
            coroutineTestExtension.testDispatcher,
            MutableStateFlow(itemAlbumModels)
        )
            .viewCreated()
            .verifyViewStates(loading, success)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `load Albums failure`() = coroutineTestExtension.testDispatcher.runBlockingTest {
        AlbumsFragmentViewModelRobo(
            this,
            coroutineTestExtension.testDispatcher
        )
            .viewCreated()
            .verifyViewStates(loading, failure)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `retry load Albums success`() = coroutineTestExtension.testDispatcher.runBlockingTest {
        AlbumsFragmentViewModelRobo(
            this,
            coroutineTestExtension.testDispatcher,
            MutableStateFlow(itemAlbumModels)
        )
            .retry()
            .verifyViewStates(loading, success)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `retry load Albums failure`() = coroutineTestExtension.testDispatcher.runBlockingTest {
        AlbumsFragmentViewModelRobo(
            this,
            coroutineTestExtension.testDispatcher
        )
            .retry()
            .verifyViewStates(loading, failure)
    }
}