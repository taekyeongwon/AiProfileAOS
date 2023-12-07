package com.example.aiprofileaos.ui.main

import android.util.Log
import androidx.activity.viewModels
import com.example.aiprofileaos.core.factory.ViewModelFactory
import com.example.aiprofileaos.core.network.base.NetResult
import com.example.aiprofileaos.core.util.setOnSingleClickListener
import com.example.aiprofileaos.databinding.ActivityMainBinding
import com.example.aiprofileaos.ui.base.BaseView

class MainActivity : BaseView<MainViewModel>() {
    override val viewModel: MainViewModel by viewModels { ViewModelFactory }
    private lateinit var binding: ActivityMainBinding

    override fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getRepos("tkw")
    }

    override fun initObserver() {
//        viewModel.count.observe(this) {
//            Log.d("Test", "receive")
//            binding.tvLivedata.text = "$it"
//        }
//        viewModel.test.observe(this) {
//            Log.d("Test", "receive")
//            binding.tvLivedata.text = "$it"
//        }
        viewModel.repoData.observe(this) {
            showProgress(false)
            when(it) {
                is NetResult.Success -> {
                    binding.tvLivedata.text = it.data?.items?.get(0)?.name ?: "test"
                }
                is NetResult.Error -> {
                    Log.d("error", it.throwable?.getMessage(this) ?: "error")
                }
                is NetResult.Loading -> {
                    showProgress(true)
                }
            }

        }
    }

    override fun initListener() {
        binding.btnAcc.setOnSingleClickListener {
            viewModel.addCount()
        }
    }
}