package com.sergo_smyk.monobankcurrency.data.model

import android.os.Build
import com.sergo_smyk.monobankcurrency.data.db.model.RateLocal
import java.text.SimpleDateFormat
import java.util.*

data class Rate(
    val currencyA: Currency,
    val currencyB: Currency,
    val date: Date,
    val rateBuy: Float,
    val rateSell: Float,
    val isFavorite: Boolean,
    private val codeA: Int,
    private val codeB: Int
) {
    fun getCodeA(): Int = codeA
    fun getCodeB(): Int = codeB

    private val innerFormat: SimpleDateFormat
        get() = getFormat(Locale.getDefault())

    val formattedDate: String
        get() = innerFormat.format(date)

    companion object {

        @JvmStatic
        private var format: SimpleDateFormat? = null

        private fun getFormat(locale: Locale): SimpleDateFormat {
            if (format == null) {
                format = SimpleDateFormat("dd.MM.yyyy hh:mm:ss", locale)
            }
            return format!!
        }

        fun create(local: RateLocal): Rate {
            return Rate(
                currencyA = getInstance(local.currencyCodeA),
                currencyB = getInstance(local.currencyCodeB),
                date = Date(local.date),
                rateBuy = local.rateBuy,
                rateSell = local.rateSell,
                isFavorite = local.isFavorite,
                codeA = local.currencyCodeA,
                codeB = local.currencyCodeB
            )
        }

        private fun getInstance(numericCode: Int): Currency {
            val currencies = Currency.getAvailableCurrencies()
            for (currency in currencies) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (currency.numericCode == numericCode) {
                        return currency
                    }
                } else {
                    return Currency.getInstance("USD") // Selyavi
                }
            }
            throw IllegalArgumentException("Currency with numeric code $numericCode not found")
        }
    }
}
