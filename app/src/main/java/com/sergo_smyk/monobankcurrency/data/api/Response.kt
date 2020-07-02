package com.sergo_smyk.monobankcurrency.data.api

sealed class Response<T> {

    suspend fun <E> map(transform: suspend (T) -> E): Response<E> {
        return when (this) {
            is Success -> {
                val mappedValue = transform(value)
                Success(mappedValue)
            }
            is Loading -> Loading(isLoading)
            is Error -> Error(exception)
        }
    }

    class Success<T>(val value: T) : Response<T>()
    class Loading<T>(val isLoading: Boolean) : Response<T>()
    class Error<T>(val exception: Throwable) : Response<T>() {

        val message: String
            get() = exception.message.toString()
    }
}