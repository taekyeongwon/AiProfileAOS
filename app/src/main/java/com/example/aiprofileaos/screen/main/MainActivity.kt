package com.example.aiprofileaos.screen.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aiprofileaos.R
import com.example.aiprofileaos.core.factory.ViewModelFactory
import com.example.aiprofileaos.core.util.setOnSingleClickListener
import com.example.aiprofileaos.databinding.ActivityMainBinding
import com.example.aiprofileaos.screen.base.BaseView

class MainActivity : BaseView<MainViewModel>() {
    override val viewModel: MainViewModel by viewModels { ViewModelFactory }
    private lateinit var binding: ActivityMainBinding

    override fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun initObserver() {
        viewModel.count.observe(this) {
            binding.tvLivedata.text = "$it"
        }
    }

    override fun initListener() {
        binding.btnAcc.setOnSingleClickListener {
            viewModel.addCount()
        }
    }
}