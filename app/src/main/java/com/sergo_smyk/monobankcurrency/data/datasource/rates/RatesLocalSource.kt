package com.sergo_smyk.monobankcurrency.data.datasource.rates

import com.sergo_smyk.monobankcurrency.data.db.AppDatabase
import com.sergo_smyk.monobankcurrency.data.db.model.RateLocal
import javax.inject.Inject

class RatesLocalSource @Inject constructor(
    private val db: AppDatabase
) : RatesDataSource.Local {

    override suspend fun getRates(): List<RateLocal> {
        return db.ratesDao().getAllRates()
    }

    override suspend fun putRates(rates: List<RateLocal>) {
        db.ratesDao().insertRates(rates)
    }

    override suspend fun updateFavorite(
        currencyCodeA: Int,
        currencyCodeB: Int,
        isFavorite: Boolean
    ) {
        db.ratesDao().updateFavoriteStatus(currencyCodeA, currencyCodeB, isFavorite)
    }
}