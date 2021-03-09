package com.mohamadk.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamadk.albums.adapter.AlbumsModelWrapper
import com.mohamadk.albums.mappers.ItemAlbumModelToAlbumsModelWrapper
import com.mohamadk.albums.mappers.ItemAlbumModelToItemAlbumUiModel
import com.mohamadk.albums.usecases.LoadAlbumsUseCase
import com.mohamadk.albums.usecases.repository.db.ItemAlbumModel
import kotlinx.coroutines.CoroutineDispatcher
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
    val viewStateFlow: SharedFlow<ViewState> = _viewStateFlow
        .flatMapLatest {
            loadAlbumsUseCase.run(viewModelScope).map { items ->
                ViewState(items = mapItems(items))
            }
        }.catch {
            emit(ViewState(showError = true))
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, ViewState(showLoading = true))

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
    val items: List<AlbumsModelWrapper<*>>? = null,
    val showError: Boolean = false
)
