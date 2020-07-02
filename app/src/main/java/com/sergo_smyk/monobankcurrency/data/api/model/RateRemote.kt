package com.sergo_smyk.monobankcurrency.data.api.model

import com.sergo_smyk.monobankcurrency.data.db.model.RateLocal
import com.squareup.moshi.Json

data class RateRemote(
    @Json(name = "currencyCodeA") val currencyCodeA: Int,
    @Json(name = "currencyCodeB") val currencyCodeB: Int,
    @Json(name = "date") val date: Long,
    @Json(name = "rateBuy") val rateBuy: Float,
    @Json(name = "rateSell") val rateSell: Float
) {
    fun toLocal() = RateLocal(
        currencyCodeA, currencyCodeB, date * 1000L, rateBuy, rateSell, false
    )
}