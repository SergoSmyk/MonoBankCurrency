package com.sergo_smyk.monobankcurrency.common

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
fun SwipeRefreshLayout.swipesForRefresh(): Flow<Unit> = callbackFlow {
    val listener = SwipeRefreshLayout.OnRefreshListener { offer(Unit) }
    setOnRefreshListener(listener)
    awaitClose {
        setOnClickListener(null)
    }
}