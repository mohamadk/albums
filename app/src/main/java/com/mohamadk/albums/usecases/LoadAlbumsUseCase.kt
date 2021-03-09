package com.mohamadk.albums.usecases

import com.mohamadk.albums.usecases.repository.LoadAlbumsRepository
import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import com.mohamadk.albums.utils.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoadAlbumsUseCase @Inject constructor(private val loadAlbumsRepository: LoadAlbumsRepository) :
    UseCase<CoroutineScope, Flow<List<ItemAlbumModel>>> {

    override fun run(coroutineScope: CoroutineScope): Flow<List<ItemAlbumModel>> {
        coroutineScope.launch {
            val result = loadAlbumsRepository.loadAlbums().runCatching {}
            if (result.isFailure && loadAlbumsRepository.albumsCount() == 0) {
                throw result.exceptionOrNull()!!
            }
        }

        return loadAlbumsRepository.albums()
    }
}

class AlbumsResponse(val items: List<ItemAlbumModel>)