package com.coinstats.app.presentation.coins

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.coinstats.app.BuildConfig
import com.coinstats.app.domain.model.Coin
import com.coinstats.app.domain.usecase.GetCoinsUseCase
import com.coinstats.common.base_classes.BaseViewModel
import com.coinstats.common.extensions.disposedBy
import com.coinstats.common.extensions.subscribeMain
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(private val getCoinsUseCase: GetCoinsUseCase) :
    BaseViewModel() {
    //region Binding
    val refreshEnabled = MutableLiveData(true)
    val coinsPagingBinding = MutableLiveData<PagingData<Coin>>()
    val coinsSearchBinding = MutableLiveData<PagingData<Coin>>()
    private var searchKeyword = BehaviorSubject.createDefault("")

    fun search(keyword: String?) {
        searchKeyword.onNext(keyword ?: "")
    }
    //endregion

    //region LifeCycle
    override fun onCreate() {
        setupCoinsBinding()
        setupSearch()
    }
    //endregion

    //region Others
    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    private fun setupCoinsBinding() {
        Pager(
            config = PagingConfig(BuildConfig.ITEMS_PER_PAGE),
            remoteMediator = getCoinsUseCase.getRemoteMediator(),
            pagingSourceFactory = {
                getCoinsUseCase.getPagingSource()
            }
        )
            .flowable
            .cachedIn(viewModelScope)
            .subscribe {
                coinsPagingBinding.value = it
            }
            .disposedBy(compositeDisposable)
    }

    private fun setupSearch(){
        searchKeyword
            .skip(1)
            .debounce(BuildConfig.SEARCH_DELAY_SEC, TimeUnit.SECONDS)
            .flatMapSingle { query -> getCoinsUseCase.searchCoins(query).map { query to it } }
            .subscribeMain(onNext = {
                val isSearching = it.first.isBlank().not()
                if (isSearching) {
                    coinsSearchBinding.value = PagingData.from(it.second)
                } else {
                    coinsPagingBinding.value = coinsPagingBinding.value
                }
                refreshEnabled.value = isSearching.not()
            })
            .disposedBy(compositeDisposable)
    }
    //endregion
}