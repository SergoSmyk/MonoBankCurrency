package com.sergo_smyk.monobankcurrency.data.repository

import com.sergo_smyk.monobankcurrency.data.api.Response
import com.sergo_smyk.monobankcurrency.data.api.model.RateRemote
import com.sergo_smyk.monobankcurrency.data.datasource.rates.RatesDataSource
import com.sergo_smyk.monobankcurrency.data.db.model.RateLocal
import com.sergo_smyk.monobankcurrency.data.model.Rate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val local: RatesDataSource.Local,
    private val remote: RatesDataSource.Remote
) : RatesRepository {

    override suspend fun getRates(): List<Rate> {
        return local.getRates().map { Rate.create(it) }
    }

    override suspend fun refreshRates(): Flow<Response<List<Rate>>> {
        return remote.getNewRates().map { response ->
            response.map { remoteRates ->
                val localRates = local.getRates()
                val newRates = combineRemoteWithLocal(localRates, remoteRates)
                    .filter { it.rateBuy > 0 && it.rateSell > 0 }
                local.putRates(newRates)
                newRates.map {
                    Rate.create(it)
                }
            }
        }
    }

    private fun combineRemoteWithLocal(
        local: List<RateLocal>,
        remote: List<RateRemote>
    ): List<RateLocal> {
        val pairs = mutableListOf<Pair<RateLocal, RateRemote>>()
        remote.forEach { remoteRate ->
            val pair = local.firstOrNull {
                it.currencyCodeA == remoteRate.currencyCodeA
                        && it.currencyCodeB == remoteRate.currencyCodeB
            }?.let { localRate ->
                localRate to remoteRate
            } ?: remoteRate.toLocal() to remoteRate

            pairs.add(pair)
        }

        return pairs.map { (l, r) -> l.combine(r) }
    }

    override suspend fun changeFavoriteStatus(rate: Rate): List<Rate> {
        val changedFavorite = !rate.isFavorite
        local.updateFavorite(rate.getCodeA(), rate.getCodeB(), changedFavorite)
        return getRates()
    }
}