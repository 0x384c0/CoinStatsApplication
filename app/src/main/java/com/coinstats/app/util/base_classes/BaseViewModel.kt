package com.coinstats.app.util.base_classes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    //region Binding
    val showAlertBinding = MutableLiveData<Throwable>()
    val showAlertStringBinding = MutableLiveData<String>()
    val showLoadingBinding = MutableLiveData(false)

    open fun onCreate() {}
    open fun onResume() {}
    open fun onPause() {}
    open fun onDestroy() {
        compositeDisposable.dispose()
    }
    //endregion

    //region Others
    protected val compositeDisposable = CompositeDisposable()

    fun showAlert(e: Throwable) {
        hideLoading()
        showAlertBinding.postValue(e)
    }

    fun showAlert(text: String) {
        hideLoading()
        showAlertStringBinding.postValue(text)
    }

    fun showLoading() {
        showLoadingBinding.postValue(true)
    }

    fun hideLoading() {
        showLoadingBinding.postValue(false)
    }
    //endregion
}