package com.coinstats.app.presentation.coins.adapter

import androidx.recyclerview.widget.DiffUtil
import com.coinstats.app.domain.model.Coin

class CoinsDiffCallback : DiffUtil.ItemCallback<Coin>() {
    override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem == newItem
    }
}