package com.sergo_smyk.monobankcurrency.data.datasource.rates

import com.sergo_smyk.monobankcurrency.data.api.ExchangeRateApiService
import com.sergo_smyk.monobankcurrency.data.api.Response
import com.sergo_smyk.monobankcurrency.data.api.model.RateRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class RatesRemoteSource @Inject constructor(
    private val service: ExchangeRateApiService
) : RatesDataSource.Remote {

    override suspend fun getNewRates(): Flow<Response<List<RateRemote>>> {

        return flow {
            emit(Response.Loading<List<RateRemote>>(true))
            Timber.d("Response.Loading true")
            val rates = service.getNewRates()
            emit(Response.Success(rates))
            Timber.d("Response.Success rates size : ${rates.size}}")
            emit(Response.Loading<List<RateRemote>>(false))
            Timber.d("Response.Loading false")
        }.catch { e ->
            emit(Response.Error(e))
            Timber.d("Response.Error $e")
            emit(Response.Loading<List<RateRemote>>(false))
            Timber.d("Response.Loading false")
        }
    }
}