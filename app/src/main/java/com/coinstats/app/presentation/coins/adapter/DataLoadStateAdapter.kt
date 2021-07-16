package com.coinstats.app.presentation.coins.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.coinstats.app.databinding.ItemNetworkStateBinding

class DataLoadStateAdapter(
    private val adapter: CoinsAdapter,
    private val layoutInflater: LayoutInflater
) : LoadStateAdapter<NetworkStateItemViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(
            ItemNetworkStateBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        ) { adapter.retry() }
    }
}