package com.mohamadk.albums.mappers

import com.mohamadk.albums.adapter.listing.ItemAlbumUiModel
import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ItemAlbumModelToItemAlbumUiModelTest{
    private val itemAlbumModel = ItemAlbumModel(1, "title")
    private val itemAlbumUiModel = ItemAlbumUiModel(1, "title")

    @Test
    fun map() {
        val itemAlbumUiModelMapped = ItemAlbumModelToItemAlbumUiModel()
            .map(itemAlbumModel)
        assertEquals(itemAlbumUiModel, itemAlbumUiModelMapped)
    }
}