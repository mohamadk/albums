package com.mohamadk.albums

import com.mohamadk.albums.usecases.AlbumsResponse
import com.mohamadk.albums.usecases.ItemAlbumModelToAlbumsModelWrapper
import com.mohamadk.albums.usecases.LoadAlbumsUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.jupiter.api.Assertions.assertEquals

class AlbumsFragmentViewModelRobo @ExperimentalCoroutinesApi constructor(
    testCoroutineScope: TestCoroutineScope,
    testCoroutineDispatcher: TestCoroutineDispatcher,
    vararg albumsResponse: AlbumsResponse
) {

    private val viewModel: AlbumsFragmentViewModel
    private val loadAlbumsUseCase: LoadAlbumsUseCase = mock()
    private val error = IllegalStateException("something went wrong :O")
    /**
     * didn't mock mapper since mock will do the same job as implementation itself
     */
    private val itemAlbumModelToAlbumsModelWrapper = ItemAlbumModelToAlbumsModelWrapper()
    private val viewStates = mutableListOf<ViewState>()
    private val viewStateJob: Job

    init {
        mockLoadAlbumsUseCase(albumsResponse)

        viewModel = AlbumsFragmentViewModel(
            testCoroutineDispatcher,
            loadAlbumsUseCase,
            itemAlbumModelToAlbumsModelWrapper
        )

        viewStateJob = testCoroutineScope.launch {
            viewModel.viewStateFlow.collect {
                viewStates.add(it)
            }
        }
    }

    private fun mockLoadAlbumsUseCase(albumsResponse: Array<out AlbumsResponse>) {
        when {
            albumsResponse.isEmpty() -> {
                whenever(loadAlbumsUseCase.run(any())).thenThrow(error)
            }
            albumsResponse.size == 1 -> {
                whenever(loadAlbumsUseCase.run(any())).thenReturn(albumsResponse[0])
            }
            albumsResponse.size > 1 -> {
                whenever(loadAlbumsUseCase.run(any())).thenReturn(
                    albumsResponse[0],
                    *albumsResponse.sliceArray(1 until albumsResponse.size)
                )
            }
        }
    }

    fun viewCreated(): AlbumsFragmentViewModelRobo {
        viewModel.viewCreated()

        return this
    }

    fun verify(vararg viewState: ViewState): AlbumsFragmentViewModelRobo {
        assertEquals(viewState.toList(), viewStates)
        viewStateJob.cancel()

        return this
    }
}
