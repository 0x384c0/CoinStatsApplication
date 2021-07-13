package com.coinstats.app.presentation.base

import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.coinstats.app.util.base_classes.BaseMVVMViewBindingActivity

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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    //endregion
}