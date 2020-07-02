package com.sergo_smyk.monobankcurrency.common

import kotlinx.coroutines.flow.Flow

interface ViewEventFlow<E> {
    fun viewEvents(): Flow<E>
}