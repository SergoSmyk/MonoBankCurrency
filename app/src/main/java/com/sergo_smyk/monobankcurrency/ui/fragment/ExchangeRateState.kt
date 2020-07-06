package com.sergo_smyk.monobankcurrency.ui.fragment

import com.sergo_smyk.monobankcurrency.data.model.Rate

sealed class ExchangeRateState {
    class ErrorSnack(val errorText: String) : ExchangeRateState()
    class UpdatedRates(val rates: List<Rate>) : ExchangeRateState()
    class ProgressStatus(val isLoading: Boolean) : ExchangeRateState()
    object Empty : ExchangeRateState()
}