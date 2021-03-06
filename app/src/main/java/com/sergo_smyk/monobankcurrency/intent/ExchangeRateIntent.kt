package com.sergo_smyk.monobankcurrency.intent

import com.sergo_smyk.monobankcurrency.common.IntentFactory
import com.sergo_smyk.monobankcurrency.data.api.Response
import com.sergo_smyk.monobankcurrency.data.model.Rate
import com.sergo_smyk.monobankcurrency.data.repository.RatesRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.sergo_smyk.monobankcurrency.ui.fragment.ExchangeRateState as ERState
import com.sergo_smyk.monobankcurrency.ui.fragment.ExchangeRateViewEvent as ERViewEvent

@ExperimentalCoroutinesApi
class ExchangeRateIntent @Inject constructor(
    private val scope: CoroutineScope,
    private val repository: RatesRepository
) : IntentFactory<ERViewEvent, ERState> {

    private val modelChannel = ConflatedBroadcastChannel<ERState>()

    override fun process(viewEvent: ERViewEvent) {
        when (viewEvent) {
            is ERViewEvent.RefreshRates -> refreshRates()
            is ERViewEvent.ShowSavedRates -> showSavedRates()
            is ERViewEvent.ChangeFavoriteStatus -> changeFavoriteStatus(viewEvent.rate)
        }
    }

    @FlowPreview
    override fun modelStates() = modelChannel.asFlow().distinctUntilChanged()

    override fun cancel() {
        modelChannel.close()
        scope.cancel()
    }

    private fun updateProgressState(isLoading: Boolean) {
        modelChannel.offer(ERState.ProgressStatus(isLoading))
    }

    private fun showErrorMessage(message: String) {
        modelChannel.offer(ERState.ErrorSnack(message))
    }

    private fun updateRatesList(rates: List<Rate>) {
        val sortedRates = rates.sortedBy { rate -> rate.isFavorite }.reversed()
        modelChannel.offer(ERState.UpdatedRates(sortedRates))
    }

    private fun refreshRates() {
        scope.launch {
            repository.refreshRates().onEach { resp ->
                when (resp) {
                    is Response.Success -> updateRatesList(resp.value)
                    is Response.Error -> showErrorMessage(resp.message)
                    is Response.Loading -> updateProgressState(resp.isLoading)
                }
            }.launchIn(this)
        }
    }

    private fun changeFavoriteStatus(rate: Rate) {
        scope.launch {
            val updatedRates = repository.changeFavoriteStatus(rate)
            updateRatesList(updatedRates)
        }
    }

    private fun showSavedRates() {
        scope.launch {
            val rates = repository.getRates()
            updateRatesList(rates)
        }
    }
}