package com.mohamadk.albums.utils

interface UseCase<INPUT, OUTPUT> {

    fun run(input: INPUT): OUTPUT
}
