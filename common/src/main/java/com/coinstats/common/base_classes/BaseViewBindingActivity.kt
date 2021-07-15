package com.coinstats.common.base_classes

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingActivity<T:ViewBinding>:AppCompatActivity() {
    //region LifeCycle
    protected lateinit var binding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateViewBinding(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupView()
    }
    //endregion

    //region Abstract
    /**
     * called in onCreate when ViewBinding layout is needed
     *
     * @property layoutInflater layoutInflater from onCreate
     * @return Created ViewBinding
     */
    abstract fun inflateViewBinding(layoutInflater: LayoutInflater): T

    /**
     * called after view is initialized
     */
    abstract fun setupView()
    //endregion
}