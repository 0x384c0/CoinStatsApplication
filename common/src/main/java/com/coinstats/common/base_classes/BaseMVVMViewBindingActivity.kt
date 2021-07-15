package com.coinstats.common.base_classes

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseMVVMViewBindingActivity<T: ViewBinding>: BaseViewBindingActivity<T>(){

    //region LifeCycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindData()
    }

    override fun onResume() {
        super.onResume()
        callOnCreateIfNeeded()
        _viewModels.forEach { it.onResume() }
    }

    override fun onPause() {
        super.onPause()
        _viewModels.forEach { it.onPause() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewModels.forEach { it.onDestroy() }
    }
    //endregion

    //region Private
    private val _viewModels = mutableListOf<BaseViewModel>()

    @MainThread
    protected fun <VM : ViewModel> getViewModel(vmClass: Class<VM>): VM {
        val vm = ViewModelProvider(this, defaultViewModelProviderFactory).get(vmClass)
        if (vm is BaseViewModel) {
            bindViewModel(vm)
        }
        return vm
    }

    private fun bindViewModel(viewModel: BaseViewModel) {
        if (!_viewModels.contains(viewModel))
            _viewModels.add(viewModel)
        viewModel.showAlertBinding.observe(this) { showAlert(it) }
        viewModel.showAlertStringBinding.observe(this) { showAlert(it) }
    }

    private var onCreateWasNotCalled = true
    private fun callOnCreateIfNeeded() {
        if (onCreateWasNotCalled) {
            _viewModels.forEach { it.onCreate() }
            onCreateWasNotCalled = false
        }
    }
    //endregion

    //region Abstract
    abstract fun bindData()
    abstract fun showAlert(throwable:Throwable)
    abstract fun showAlert(string:String)
    //endregion
}