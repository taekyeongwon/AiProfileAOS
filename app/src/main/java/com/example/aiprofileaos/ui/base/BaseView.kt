package com.example.aiprofileaos.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

abstract class BaseView<T: BaseViewModel>: AppCompatActivity() {
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

        observeProgress()
    }

    private fun observeProgress() {
        viewModel.progressFlag.observe(this, Observer {

        })
    }
}