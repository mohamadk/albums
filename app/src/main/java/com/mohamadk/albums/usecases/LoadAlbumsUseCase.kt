package com.mohamadk.albums.usecases

import com.mohamadk.albums.usecases.repository.LoadAlbumsRepository
import com.mohamadk.albums.usecases.repository.NetworkError
import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import com.mohamadk.albums.utils.UseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LoadAlbumsUseCase @Inject constructor(
    private val loadAlbumsRepository: LoadAlbumsRepository
    ) : UseCase<CoroutineScope, Flow<List<ItemAlbumModel>>> {

    val netWorkFailureStateFlow: MutableStateFlow<NetworkError?> = loadAlbumsRepository.netWorkFailureStateFlow

    override fun run(coroutineScope: CoroutineScope): Flow<List<ItemAlbumModel>> {
        return loadAlbumsRepository.albums(coroutineScope)
    }
}