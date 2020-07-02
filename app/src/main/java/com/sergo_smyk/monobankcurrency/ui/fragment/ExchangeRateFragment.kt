package com.sergo_smyk.monobankcurrency.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.sergo_smyk.monobankcurrency.R
import com.sergo_smyk.monobankcurrency.common.ViewEventFlow
import com.sergo_smyk.monobankcurrency.common.swipesForRefresh
import com.sergo_smyk.monobankcurrency.data.model.Rate
import com.sergo_smyk.monobankcurrency.databinding.FragmentExchangeRateBinding
import com.sergo_smyk.monobankcurrency.databinding.ItemRateBinding
import com.sergo_smyk.monobankcurrency.intent.ExchangeRateIntent
import com.sergo_smyk.monobankcurrency.ui.binder.RatesListBinder
import com.sergo_smyk.monobankcurrency.ui.diffcallback.RatesDiffCallback
import com.sergo_smyk.recycler_binding.recycler_adapters.BindAdapter
import com.sergo_smyk.recycler_binding.recycler_adapters.LiteBindAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
@FlowPreview
@ExperimentalCoroutinesApi
class ExchangeRateFragment : Fragment(R.layout.fragment_exchange_rate),
    ViewEventFlow<ExchangeRateViewEvent> {

    @Inject
    lateinit var ratesListBinder: RatesListBinder

    @Inject
    lateinit var intentFactory: ExchangeRateIntent


    private val mainScope = MainScope()
    private lateinit var binding: FragmentExchangeRateBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentExchangeRateBinding.bind(view)
        initViews()
        launchFlowObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainScope.cancel()
        intentFactory.cancel()
    }

    override fun viewEvents(): Flow<ExchangeRateViewEvent> {
        val favoriteClicksFlow = ratesListBinder.favoriteClicksFlows
        val flows = listOf(
            favoriteClicksFlow.receiveAsFlow().map {
                ExchangeRateViewEvent.ChangeFavoriteStatus(it)
            },
            binding.swipeRefreshLayout.swipesForRefresh().map {
                ExchangeRateViewEvent.RefreshRates
            }
        )

        return flows.asFlow().flattenMerge(flows.size)
    }

    private fun initViews() {
        binding.adapter = BindAdapter<ItemRateBinding, Rate>(R.layout.item_rate).apply {
            setItemBinder(ratesListBinder)
            setDiffCallback(RatesDiffCallback())
        }

        intentFactory.process(ExchangeRateViewEvent.ShowSavedRates)
    }

    private fun launchFlowObservers() {
        intentFactory.modelStates().onEach { state ->
            handleState(state)
        }.launchIn(mainScope)

        viewEvents().onEach { event ->
            intentFactory.process(event)
        }.launchIn(mainScope)
    }

    private fun handleState(state: ExchangeRateState) {
        when (state) {
            is ExchangeRateState.UpdatedRates -> {
                binding.items = state.rates
                Timber.d("ShowRates size = ${state.rates.size}")
            }
            is ExchangeRateState.ProgressStatus -> {
                binding.swipeRefreshLayout.isRefreshing = state.isLoading
                Timber.d("IsRefreshing = ${state.isLoading}")
            }
            is ExchangeRateState.ErrorSnack -> {
                Snackbar
                    .make(binding.swipeRefreshLayout, state.errorText, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}