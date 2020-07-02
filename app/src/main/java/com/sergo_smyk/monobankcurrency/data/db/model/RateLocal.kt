package com.sergo_smyk.monobankcurrency.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.sergo_smyk.monobankcurrency.data.api.model.RateRemote

@Entity(tableName = "rates", primaryKeys = ["currencyCodeA","currencyCodeB"])
data class RateLocal(
    @ColumnInfo(name = "currencyCodeA") val currencyCodeA: Int,
    @ColumnInfo(name = "currencyCodeB") val currencyCodeB: Int,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "rateBuy") val rateBuy: Float,
    @ColumnInfo(name = "rateSell") val rateSell: Float,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean
) {
    fun combine(remote: RateRemote): RateLocal {
        return this.copy(
            date = remote.date * 1000L,
            rateBuy = remote.rateBuy,
            rateSell = remote.rateSell
        )
    }
}