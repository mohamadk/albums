package com.mohamadk.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamadk.albums.adapter.AlbumsModelWrapper
import com.mohamadk.albums.mappers.ItemAlbumModelToAlbumsModelWrapper
import com.mohamadk.albums.mappers.ItemAlbumModelToItemAlbumUiModel
import com.mohamadk.albums.usecases.LoadAlbumsUseCase
import com.mohamadk.albums.usecases.repository.NetworkError
import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlbumsFragmentViewModel @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val loadAlbumsUseCase: LoadAlbumsUseCase,
    private val itemAlbumModelToAlbumsModelWrapper: ItemAlbumModelToAlbumsModelWrapper,
    private val itemAlbumModelToItemAlbumUiModel: ItemAlbumModelToItemAlbumUiModel
) : ViewModel() {

    private val _viewStateFlow = MutableSharedFlow<Unit>()

    @ExperimentalCoroutinesApi
    val viewStateFlow: SharedFlow<ViewState> = _viewStateFlow
        .flatMapLatest {
            _errorStateFlow.emit(null)

            loadAlbumsUseCase.run(viewModelScope).map { items ->
                ViewState(items = mapItems(items), showLoading = items.isEmpty())
            }
        }.catch {
            //this shouldn't happen
            _errorStateFlow.emit(NetworkError())
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, ViewState(showLoading = true))

    private val _errorStateFlow: MutableStateFlow<NetworkError?> =
        loadAlbumsUseCase.netWorkFailureStateFlow
    val errorStateFlow: StateFlow<NetworkError?> = _errorStateFlow

    private fun mapItems(items: List<ItemAlbumModel>): List<AlbumsModelWrapper<*>> {
        return items.map { itemAlbumModel ->
            itemAlbumModelToAlbumsModelWrapper.map(
                itemAlbumModelToItemAlbumUiModel.map(itemAlbumModel)
            )
        }
    }

    fun viewCreated() {
        viewModelScope.launch(ioDispatcher) {
            _viewStateFlow.emit(Unit)
        }
    }

    fun retry() {
        viewModelScope.launch(ioDispatcher) {
            _viewStateFlow.emit(Unit)
        }
    }
}

data class ViewState(
    val showLoading: Boolean = false,
    val items: List<AlbumsModelWrapper<*>>? = null
)
