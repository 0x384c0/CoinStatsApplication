package com.coinstats.app.presentation.coins

import android.view.LayoutInflater
import com.coinstats.app.databinding.ActivityCoinsBinding
import com.coinstats.app.databinding.ItemCoinBinding
import com.coinstats.app.domain.model.Coin
import com.coinstats.app.presentation.base.BaseActivity
import com.coinstats.app.util.adapters.SingleViewBindingAdapter
import com.coinstats.app.util.extensions.toAmount
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinsActivity : BaseActivity<ActivityCoinsBinding>() {
    //region Loading
    override fun showLoading() {

    }

    override fun hideLoading() {

    }
    //endregion

    //region View initialization
    override fun inflateViewBinding(layoutInflater: LayoutInflater): ActivityCoinsBinding {
        return ActivityCoinsBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        SingleViewBindingAdapter<Coin, ItemCoinBinding>(
            inflate = { ItemCoinBinding.inflate(layoutInflater, it, false) },
            bindViewHandler = { binding, coin ->
                binding.textViewName.text = coin.name
                binding.textViewPrice.text = coin.price.toAmount("$")
            }
        )
    }

    override fun setupView() {
        binding.recyclerView.adapter = adapter
    }
    //endregion

    //region MVVM
    private val viewModel by lazy { getViewModel(CoinsViewModel::class.java) }
    override fun bindData() {
        viewModel.coinsLiveData.observe(this) {
            adapter.data = it
        }
        viewModel.refresh()
    }
    //endregion
}