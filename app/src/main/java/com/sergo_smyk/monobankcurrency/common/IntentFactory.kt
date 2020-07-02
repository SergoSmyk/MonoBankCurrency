package com.sergo_smyk.monobankcurrency.common

import kotlinx.coroutines.flow.Flow

interface IntentFactory<T, E> {
    fun process(viewEvent: T)

    fun modelStates(): Flow<E>

    fun cancel()
}