package com.mohamadk.albums.usecases

import com.mohamadk.albums.adapter.listing.ItemAlbumModel
import com.mohamadk.albums.utils.UseCase
import javax.inject.Inject

class LoadAlbumsUseCase @Inject constructor(private val loadAlbumsRepository: LoadAlbumsRepository)
    : UseCase<Unit, AlbumsResponse> {

    override fun run(unit: Unit): AlbumsResponse {
        return loadAlbumsRepository.loadAlbums()
    }
}

class AlbumsResponse(val items: List<ItemAlbumModel>)