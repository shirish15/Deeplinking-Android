package com.example.myapplication.utils


//wraps api result in a sealed class
sealed class ApiResultWrapper<T : Any> {
    class Success<T : Any>(val data: T) : ApiResultWrapper<T>()
    class Error<T : Any>(val message: String?) : ApiResultWrapper<T>()
}