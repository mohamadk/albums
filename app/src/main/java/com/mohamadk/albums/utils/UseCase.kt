package com.mohamadk.albums.utils

interface UseCase<INPUT, OUTPUT> {

    suspend fun run(input: INPUT): OUTPUT

}
