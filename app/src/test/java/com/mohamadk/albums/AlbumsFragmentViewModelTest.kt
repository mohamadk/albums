package com.mohamadk.albums

import com.mohamadk.albums.adapter.AlbumsListingItemTypes
import com.mohamadk.albums.adapter.AlbumsModelWrapper
import com.mohamadk.albums.adapter.listing.ItemAlbumModel
import com.mohamadk.albums.usecases.AlbumsResponse
import com.mohamadk.albums.utils.CoroutineTestExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension


internal class AlbumsFragmentViewModelTest {

    companion object {
        @ExperimentalCoroutinesApi
        @RegisterExtension
        @JvmField
        val coroutineTestExtension = CoroutineTestExtension()
    }

    private val itemAlbumModel = ItemAlbumModel("title")
    private val itemAlbumModels = listOf(itemAlbumModel)
    private val albumsModelWrappers =
        listOf(AlbumsModelWrapper(AlbumsListingItemTypes.SingleTitleItemType, itemAlbumModel))

    private val loading = ViewState(showLoading = true, items = null, showError = false)
    private val success =
        ViewState(showLoading = false, items = albumsModelWrappers, showError = false)
    private val failure = ViewState(showLoading = false, items = null, showError = true)


    @ExperimentalCoroutinesApi
    @Test
    fun `load Albums success`() = coroutineTestExtension.testDispatcher.runBlockingTest {
        AlbumsFragmentViewModelRobo(
            this,
            coroutineTestExtension.testDispatcher,
            AlbumsResponse(itemAlbumModels)
        )
            .viewCreated()
            .verify(loading, success)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `load Albums failure`() = coroutineTestExtension.testDispatcher.runBlockingTest {
        AlbumsFragmentViewModelRobo(
            this,
            coroutineTestExtension.testDispatcher
        )
            .viewCreated()
            .verify(loading, failure)
    }

}