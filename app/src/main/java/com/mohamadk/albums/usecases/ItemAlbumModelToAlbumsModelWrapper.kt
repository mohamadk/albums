package com.mohamadk.albums.usecases

import com.mohamadk.albums.adapter.AlbumsListingItemTypes
import com.mohamadk.albums.adapter.AlbumsModelWrapper
import com.mohamadk.albums.adapter.listing.ItemAlbumModel
import com.mohamadk.albums.utils.Mapper
import javax.inject.Inject

class ItemAlbumModelToAlbumsModelWrapper @Inject constructor() :
    Mapper<ItemAlbumModel, AlbumsModelWrapper<*>> {

    override fun map(input: ItemAlbumModel): AlbumsModelWrapper<*> {
        return AlbumsModelWrapper(
            AlbumsListingItemTypes.SingleTitleItemType,
            input
        )
    }
}
