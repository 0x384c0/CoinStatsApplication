package com.coinstats.app.presentation.coins

import android.view.LayoutInflater
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.coinstats.app.R
import com.coinstats.app.databinding.ActivityCoinsBinding
import com.coinstats.app.presentation.base.BaseActivity
import com.coinstats.app.presentation.coins.adapter.CoinsAdapter
import com.coinstats.app.presentation.coins.adapter.DataLoadStateAdapter
import com.coinstats.common.extensions.setOnQueryListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CoinsActivity : BaseActivity<ActivityCoinsBinding>() {

    //region View initialization
    override fun inflateViewBinding(layoutInflater: LayoutInflater): ActivityCoinsBinding {
        return ActivityCoinsBinding.inflate(layoutInflater)
    }

    private val adapter by lazy { CoinsAdapter(layoutInflater) }

    override fun setupView() {
        setupRecyclerView()
        setupSwipeToRefresh()
        setupLoadStateListener()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter.withLoadStateFooter(DataLoadStateAdapter(adapter))
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
        adapter.addLoadStateListener { loadStates ->
            binding.swipeRefreshLayout.isRefreshing =
                loadStates.mediator?.refresh is LoadState.Loading
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupLoadStateListener(){
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .debounce(TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS))
                .collect { loadStates ->
                    val error = listOfNotNull(
                        loadStates.mediator?.append,
                        loadStates.mediator?.refresh,
                        loadStates.mediator?.prepend
                    )
                        .filterIsInstance<LoadState.Error>()
                        .firstOrNull()
                    if (error != null) {
                        viewModel.showAlert(error.error)
                    }
                }
        }
    }
    //endregion

    //region Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchView = (menu?.findItem(R.id.search)?.actionView as? SearchView)
        searchView?.setOnQueryListener { viewModel.search(it) }
        return true
    }
    //endregion

    //region MVVM
    private val viewModel by lazy { getViewModel(CoinsViewModel::class.java) }
    override fun bindData() {
        viewModel.coinsPagingBinding.observe(this) {
            adapter.submitData(lifecycle, it)
        }
        viewModel.coinsSearchBinding.observe(this) {
            adapter.submitData(lifecycle, it)
        }
        viewModel.refreshEnabled.observe(this) {
            binding.swipeRefreshLayout.isEnabled = it
        }
    }
    //endregion
}