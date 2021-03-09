package com.mohamadk.albums.mappers

import com.mohamadk.albums.adapter.AlbumsListingItemTypes
import com.mohamadk.albums.adapter.AlbumsModelWrapper
import com.mohamadk.albums.adapter.listing.ItemAlbumUiModel
import com.mohamadk.albums.utils.Mapper
import javax.inject.Inject

class ItemAlbumModelToAlbumsModelWrapper @Inject constructor() :
    Mapper<ItemAlbumUiModel, AlbumsModelWrapper<*>> {

    override fun map(input: ItemAlbumUiModel): AlbumsModelWrapper<*> {
        return AlbumsModelWrapper(
            AlbumsListingItemTypes.SingleTitleItemType,
            input
        )
    }
}
