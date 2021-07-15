package com.coinstats.app.presentation.coins.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.coinstats.app.databinding.ItemCoinBinding
import com.coinstats.app.domain.model.Coin

class CoinsAdapter(private val layoutInflater: LayoutInflater) :
    PagingDataAdapter<Coin, CoinsViewHolder>(CoinsDiffCallback()) {

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        return CoinsViewHolder(ItemCoinBinding.inflate(layoutInflater, parent, false))
    }
}

