package com.example.aiprofileaos.screen.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

abstract class BaseView<T: BaseViewModel>: AppCompatActivity() {
    companion object {
        var clickable = true
    }

    abstract val layoutResId: Int
    abstract val viewModel: T

    abstract fun initView()
    abstract fun initObserver()
    abstract fun initListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(layoutResId != -1) setContentView(layoutResId)

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