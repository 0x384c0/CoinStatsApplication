package com.coinstats.app.presentation.coins.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.coinstats.app.databinding.ItemCoinBinding
import com.coinstats.app.domain.model.Coin

class CoinsAdapter(private val layoutInflater: LayoutInflater) :
    PagingDataAdapter<Coin, CoinsViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        return CoinsViewHolder(ItemCoinBinding.inflate(layoutInflater, parent, false))
    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         *
         * @see DiffUtil
         */
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Coin>() {
            override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem == newItem
            }
        }
    }
}

