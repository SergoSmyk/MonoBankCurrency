package com.sergo_smyk.monobankcurrency.ui.binder

import com.sergo_smyk.monobankcurrency.data.model.Rate
import com.sergo_smyk.monobankcurrency.databinding.ItemRateBinding
import com.sergo_smyk.recycler_binding.binder.ItemBinder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
class RatesListBinder : ItemBinder<ItemRateBinding, Rate> {

    val favoriteClicksFlows = MutableStateFlow(Rate.EMPTY)

    override fun onBind(binding: ItemRateBinding, item: Rate, position: Int) {
        binding.rate = item
    }

    override fun onBindListeners(binding: ItemRateBinding) {
        binding.favorite.setOnClickListener {
            binding.rate?.let { rate ->
                favoriteClicksFlows.value = rate
            }
        }
    }

    override fun onPostBind(binding: ItemRateBinding, item: Rate, position: Int) {}
}