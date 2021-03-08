package com.mohamadk.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamadk.albums.adapter.AlbumsModelWrapper
import com.mohamadk.albums.usecases.ItemAlbumModelToAlbumsModelWrapper
import com.mohamadk.albums.usecases.LoadAlbumsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlbumsFragmentViewModel @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val loadAlbumsUseCase: LoadAlbumsUseCase,
    private val itemAlbumModelToAlbumsModelWrapper: ItemAlbumModelToAlbumsModelWrapper
) : ViewModel() {

    private val _viewStateFlow = MutableStateFlow(ViewState(true))
    val viewStateFlow: StateFlow<ViewState> = _viewStateFlow

    fun viewCreated() {
        viewModelScope.launch(ioDispatcher) {
            loadAlbums()
        }
    }

    private fun loadAlbums() {
        try {
            _viewStateFlow.value = ViewState(showLoading = true)
            val albums = loadAlbumsUseCase.run(Unit)
            _viewStateFlow.value = ViewState(items = albums.items.map {
                itemAlbumModelToAlbumsModelWrapper.map(it)
            })
        } catch (e: Exception) {
            _viewStateFlow.value = ViewState(showError = true)
        }
    }
}

data class ViewState(
    val showLoading: Boolean = false,
    val items: List<AlbumsModelWrapper<*>>? = null,
    val showError: Boolean = false
)
