package com.coinstats.app.presentation.base

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.coinstats.common.base_classes.BaseMVVMViewBindingActivity
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<T : ViewBinding> : BaseMVVMViewBindingActivity<T>() {
    //region Overrides
    override fun showAlert(throwable: Throwable) {
        showAlert(throwable.localizedMessage ?: throwable.toString())
    }

    override fun showAlert(string: String) {
        toast(string)
    }
    //endregion

    //region Others
    private fun toast(message: String) {
        val root = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT)
            .show()
    }
    //endregion
}