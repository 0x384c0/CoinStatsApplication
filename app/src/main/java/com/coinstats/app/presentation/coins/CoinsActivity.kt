package com.coinstats.app.presentation.coins

import android.app.SearchManager
import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.coinstats.app.R
import com.coinstats.app.databinding.ActivityCoinsBinding
import com.coinstats.app.presentation.base.BaseActivity
import com.coinstats.app.presentation.coins.adapter.CoinsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinsActivity : BaseActivity<ActivityCoinsBinding>() {
    //region Loading
    override fun showLoading() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        binding.swipeRefreshLayout.isRefreshing = false
    }
    //endregion

    //region View initialization
    override fun inflateViewBinding(layoutInflater: LayoutInflater): ActivityCoinsBinding {
        return ActivityCoinsBinding.inflate(layoutInflater)
    }

    private val adapter by lazy { CoinsAdapter(layoutInflater) }

    override fun setupView() {
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu!!.findItem(R.id.search).actionView as SearchView).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.search(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.search(newText)
                    return true
                }
            })
        }
        return true
    }
    //endregion

    //region MVVM
    private val viewModel by lazy { getViewModel(CoinsViewModel::class.java) }
    override fun bindData() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.coinsPagingBinding.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
    //endregion
}