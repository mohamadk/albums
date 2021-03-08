package com.mohamadk.albums.usecases

import com.mohamadk.albums.adapter.AlbumsListingItemTypes
import com.mohamadk.albums.adapter.AlbumsModelWrapper
import com.mohamadk.albums.adapter.listing.ItemAlbumModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ItemAlbumModelToAlbumsModelWrapperTest {

    private val itemAlbumModel = ItemAlbumModel("title")
    private val albumsModelWrapper =
        AlbumsModelWrapper(AlbumsListingItemTypes.SingleTitleItemType, itemAlbumModel)

    @Test
    fun map() {
        val albumsModelWrapperResult = ItemAlbumModelToAlbumsModelWrapper()
            .map(itemAlbumModel)

        assertEquals(albumsModelWrapper, albumsModelWrapperResult)
    }
}