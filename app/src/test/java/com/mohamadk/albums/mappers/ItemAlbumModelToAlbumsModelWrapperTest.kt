package com.mohamadk.albums.mappers

import com.mohamadk.albums.adapter.AlbumsListingItemTypes
import com.mohamadk.albums.adapter.AlbumsModelWrapper
import com.mohamadk.albums.adapter.listing.ItemAlbumUiModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ItemAlbumModelToAlbumsModelWrapperTest {

    private val itemAlbumModel = ItemAlbumUiModel(1, "title")
    private val albumsModelWrapper =
        AlbumsModelWrapper(AlbumsListingItemTypes.SingleTitleItemType, itemAlbumModel)

    @Test
    fun map() {
        val albumsModelWrapperResult = ItemAlbumModelToAlbumsModelWrapper()
            .map(itemAlbumModel)

        assertEquals(albumsModelWrapper, albumsModelWrapperResult)
    }
}