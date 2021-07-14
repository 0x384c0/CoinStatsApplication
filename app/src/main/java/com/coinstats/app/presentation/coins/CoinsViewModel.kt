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
import com.coinstats.app.util.base_classes.BaseViewModel
import com.coinstats.app.util.extensions.disposedBy
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(private val getCoinsUseCase: GetCoinsUseCase) :
    BaseViewModel() {
    val coinsPagingBinding = MutableLiveData<PagingData<Coin>>()
    private var searchKeyword = BehaviorSubject.createDefault("")

    fun search(keyword: String?) {
        searchKeyword.onNext(keyword ?: "")
    }

    override fun onCreate() {
        setupCoinsBinding()
//        searchKeyword
//            .skip(1)
//            .debounce(BuildConfig.SEARCH_DELAY_SEC, TimeUnit.SECONDS)
//            .flatMapSingle { getCoinsUseCase.searchCoins(it) }
//            .subscribeMain(onNext = {
//                if (it.isNotEmpty())
//                    coinsBinding.value = it
//            })
//            .disposedBy(compositeDisposable)
    }

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

}