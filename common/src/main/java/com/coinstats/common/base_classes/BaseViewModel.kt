package com.coinstats.common.base_classes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    //region Binding
    val showAlertBinding = MutableLiveData<Throwable>()
    val showAlertStringBinding = MutableLiveData<String>()

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
        showAlertBinding.postValue(e)
    }

    fun showAlert(text: String) {
        showAlertStringBinding.postValue(text)
    }
    //endregion
}