package com.coinstats.app.presentation.coins

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coinstats.app.domain.model.Coin
import com.coinstats.app.domain.usecase.GetCoinsUseCase
import com.coinstats.app.util.base_classes.BaseViewModel
import com.coinstats.app.util.extensions.disposedBy
import com.coinstats.app.util.extensions.subscribeOnMain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(private val getCoinsUseCase: GetCoinsUseCase) :
    BaseViewModel() {
    val coinsLiveData = MutableLiveData<List<Coin>>()
    private val searchQuery: String? = null

    fun refresh(){
        showLoading()
        getCoinsUseCase
            .getCoins(searchQuery)
            .subscribeOnMain(
                onNext = {
                    hideLoading()
                    coinsLiveData.value = it
                },
                onError = this::showAlert
            )
            .disposedBy(compositeDisposable)
    }

    override fun onCreate() {
        refresh()
    }
}