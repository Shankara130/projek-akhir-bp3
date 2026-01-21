package com.example.edunovel.util

sealed class Resource(val data: T? = null, val message: String? = null) {
    class Success(data: T) : Resource(data)
    class Error(message: String, data: T? = null) : Resource(data, message)
    class Loading(data: T? = null) : Resource(data)
}