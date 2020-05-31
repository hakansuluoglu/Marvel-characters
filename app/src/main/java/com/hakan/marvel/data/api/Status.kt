package com.hakan.marvel.data.api

sealed class Status<out T : Any>{
    data class Success<out T : Any>(val output : T) : Status<T>()
    data class Error(val exception: Exception)  : Status<Nothing>()
}