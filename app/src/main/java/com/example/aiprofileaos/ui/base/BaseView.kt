package com.example.aiprofileaos.ui.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseView<T: ViewModel>: AppCompatActivity() {
    companion object {
        var clickable = true
    }

    abstract val viewModel: T

    abstract fun initView()
    abstract fun initObserver()
    abstract fun initListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initObserver()
        initListener()
    }

    fun showProgress(flag: Boolean) {
        Log.d("showProgress", "$flag")
    }
}