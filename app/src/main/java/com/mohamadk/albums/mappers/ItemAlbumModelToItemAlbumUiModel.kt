package com.mohamadk.albums.mappers

import com.mohamadk.albums.adapter.listing.ItemAlbumUiModel
import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import com.mohamadk.albums.utils.Mapper
import javax.inject.Inject

class ItemAlbumModelToItemAlbumUiModel @Inject constructor() :
    Mapper<ItemAlbumModel, ItemAlbumUiModel> {

    override fun map(input: ItemAlbumModel): ItemAlbumUiModel {
        return ItemAlbumUiModel(input.id, input.title)
    }
}
