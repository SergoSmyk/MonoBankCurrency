package com.sergo_smyk.monobankcurrency.data.repository

import com.sergo_smyk.monobankcurrency.data.api.Response
import com.sergo_smyk.monobankcurrency.data.model.Rate
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    suspend fun getRates(): List<Rate>

    suspend fun refreshRates(): Flow<Response<List<Rate>>>

    suspend fun changeFavoriteStatus(rate: Rate): List<Rate>
}