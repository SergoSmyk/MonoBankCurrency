package com.sergo_smyk.monobankcurrency.ui.diffcallback

import com.sergo_smyk.monobankcurrency.data.model.Rate
import com.sergo_smyk.recycler_binding.diif_utils.DiffCallback

class RatesDiffCallback : DiffCallback<Rate>() {
    override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean {
        return oldItem.getCodeA() == newItem.getCodeA()
                && oldItem.getCodeB() == newItem.getCodeB()
    }

    override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean {
        return oldItem == newItem
    }
}