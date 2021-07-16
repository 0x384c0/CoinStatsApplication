package com.coinstats.app.presentation.coins.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.recyclerview.widget.RecyclerView
import com.coinstats.app.databinding.ItemNetworkStateBinding

/**
 * A View Holder that can display a loading or have click action.
 * It is used to show the network state of paging.
 */
class NetworkStateItemViewHolder(
    private val binding: ItemNetworkStateBinding,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(
    binding.root
) {
    private val retry = binding.retryButton
        .also {
            it.setOnClickListener { retryCallback() }
        }

    fun bindTo(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is Loading
        retry.isVisible = loadState is Error
        binding.errorMsg.isVisible = !(loadState as? Error)?.error?.message.isNullOrBlank()
        binding.errorMsg.text = (loadState as? Error)?.error?.message
    }
}
