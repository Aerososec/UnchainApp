package com.example.unchain.domain.utils

sealed interface Outcome<out T> {
    data class Success<T>(val data: T) : Outcome<T>

    sealed interface Fail : Outcome<Nothing>{
        data object NoInternet : Fail
        data object ServerBusy : Fail
        data object ServerError : Fail
        data object AuthError : Fail
        data class Unknown(val message: String?) : Fail
    }
}