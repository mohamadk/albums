package com.mohamadk.albums.app.di

import androidx.lifecycle.ViewModel
import com.mohamadk.albums.AlbumsFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelMapperModule {

    @IntoMap
    @Binds
    @ViewModelKey(AlbumsFragmentViewModel::class)
    abstract fun bindAlbumsFragmentViewModel(viewModel: AlbumsFragmentViewModel): ViewModel

}
