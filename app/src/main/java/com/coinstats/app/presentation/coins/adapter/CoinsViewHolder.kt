package com.coinstats.app.presentation.coins.adapter

import androidx.recyclerview.widget.RecyclerView
import com.coinstats.app.databinding.ItemCoinBinding
import com.coinstats.app.domain.model.Coin
import com.coinstats.common.extensions.load
import com.coinstats.common.extensions.toAmount

class CoinsViewHolder(
    private val binding: ItemCoinBinding
) : RecyclerView.ViewHolder(binding.root) {
    var coin: Coin? = null
        private set

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(coin: Coin?) {
        if (coin != null) {
            binding.imageView.load(coin.icon)
            binding.textViewName.text = coin.name
            binding.textViewPrice.text = coin.price.toAmount("$")
        }
    }
}