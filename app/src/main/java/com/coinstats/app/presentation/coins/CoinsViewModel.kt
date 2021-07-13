package com.coinstats.app.presentation.coins

import androidx.lifecycle.MutableLiveData
import com.coinstats.app.domain.model.Coin
import com.coinstats.app.domain.usecase.GetCoinsUseCase
import com.coinstats.app.util.base_classes.BaseViewModel
import com.coinstats.app.util.extensions.disposedBy
import com.coinstats.app.util.extensions.subscribeMain
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(private val getCoinsUseCase: GetCoinsUseCase) :
    BaseViewModel() {
    val coinsLiveData = MutableLiveData<List<Coin>>()
    private var searchKeyword = BehaviorSubject.createDefault<String>("")

    fun refresh() {
        showLoading()
        getCoinsUseCase
            .getCoins(searchKeyword.value)
            .subscribeMain(
                onNext = {
                    hideLoading()
                    coinsLiveData.value = it
                },
                onError = this::showAlert
            )
            .disposedBy(compositeDisposable)
    }

    fun search(keyword: String?) {
        searchKeyword.onNext(keyword ?: "")
    }

    override fun onCreate() {
        refresh()
        searchKeyword
            .skip(1)
            .debounce(3, TimeUnit.SECONDS)
            .flatMapSingle { getCoinsUseCase.searchCoins(it) }
            .subscribeMain(onNext = {
                if (it.isNotEmpty())
                    coinsLiveData.value = it
            })
            .disposedBy(compositeDisposable)
    }
}