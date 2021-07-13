package com.coinstats.app.util.base_classes

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
    abstract fun inflateViewBinding(layoutInflater:LayoutInflater):T
    abstract fun setupView()
    //endregion
}