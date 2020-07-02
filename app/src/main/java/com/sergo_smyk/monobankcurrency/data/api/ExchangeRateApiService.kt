package com.sergo_smyk.monobankcurrency.data.api

import com.sergo_smyk.monobankcurrency.data.api.model.RateRemote
import retrofit2.http.GET

interface ExchangeRateApiService {

    @GET("bank/currency")
    suspend fun getNewRates(): List<RateRemote>
}