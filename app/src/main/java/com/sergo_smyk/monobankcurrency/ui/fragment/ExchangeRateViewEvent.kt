package com.sergo_smyk.monobankcurrency.ui.fragment

import com.sergo_smyk.monobankcurrency.data.model.Rate

sealed class ExchangeRateViewEvent {

    object ShowSavedRates: ExchangeRateViewEvent()

    object RefreshRates : ExchangeRateViewEvent()

    class ChangeFavoriteStatus(val rate: Rate) : ExchangeRateViewEvent()
}