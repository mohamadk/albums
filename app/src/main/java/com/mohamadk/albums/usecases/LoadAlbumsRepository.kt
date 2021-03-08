package com.mohamadk.albums.usecases


interface LoadAlbumsRepository {

    fun loadAlbums(): AlbumsResponse
}
