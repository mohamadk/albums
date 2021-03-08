package com.mohamadk.albums.utils

interface Mapper<T, U> {

    fun map(input:T):U
}
