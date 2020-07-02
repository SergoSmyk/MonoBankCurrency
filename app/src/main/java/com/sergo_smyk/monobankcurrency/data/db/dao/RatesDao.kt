package com.sergo_smyk.monobankcurrency.data.db.dao

import androidx.room.*
import com.sergo_smyk.monobankcurrency.data.db.model.RateLocal

@Dao
interface RatesDao {

    @Query("SELECT * FROM rates")
    suspend fun getAllRates(): List<RateLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(rates: RateLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<RateLocal>)

    @Query(
        """UPDATE rates
                SET isFavorite = :isFavorite
                WHERE currencyCodeA = :currencyCodeA and currencyCodeB = :currencyCodeB"""
    )
    suspend fun updateFavoriteStatus(currencyCodeA: Int, currencyCodeB: Int, isFavorite: Boolean)
}