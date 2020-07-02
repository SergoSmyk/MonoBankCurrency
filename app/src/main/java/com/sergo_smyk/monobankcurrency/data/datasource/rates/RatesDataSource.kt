package com.sergo_smyk.monobankcurrency.data.datasource.rates

import com.sergo_smyk.monobankcurrency.data.api.Response
import com.sergo_smyk.monobankcurrency.data.api.model.RateRemote
import com.sergo_smyk.monobankcurrency.data.datasource.DataSource
import com.sergo_smyk.monobankcurrency.data.db.model.RateLocal
import kotlinx.coroutines.flow.Flow

interface RatesDataSource : DataSource {

    interface Remote : DataSource.Remote {
        suspend fun getNewRates(): Flow<Response<List<RateRemote>>>
    }

    interface Local : DataSource.Local {
        suspend fun getRates(): List<RateLocal>

        suspend fun putRates(rates: List<RateLocal>)

        suspend fun updateFavorite(currencyCodeA: Int, currencyCodeB: Int, isFavorite: Boolean)
    }

}