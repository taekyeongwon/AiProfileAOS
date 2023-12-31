package com.example.aiprofileaos.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.aiprofileaos.core.network.base.ApiResult
import com.example.aiprofileaos.data.model.GithubRepos
import com.example.aiprofileaos.data.remote.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val COUNT_STATE_KEY = "COUNT_STATE_KEY"
class MainViewModel(
    private val model: MainRepository,
    private val savedStateHandle: SavedStateHandle
    ): ViewModel() {

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> get() = _count

    private val _repoData = MutableLiveData<GithubRepos>()
    val repoData: LiveData<GithubRepos> get() = _repoData

    val test: LiveData<Int> get() = liveData(Dispatchers.IO) {
        delay(10000)
        emit(100)
    }

    init {
        Log.d("test", "init")
        _count.value = savedStateHandle[COUNT_STATE_KEY] ?: 1
    }

    fun addCount() {
        _count.value = _count.value?.plus(1)
        savedStateHandle[COUNT_STATE_KEY] = _count.value
    }

    fun getRepos(query: String) {
        viewModelScope.launch {
            when(val result = model.getRepos(query)) {
                is ApiResult.Success -> {
                    _repoData.value = result.data
                }
                is ApiResult.Error -> {

                }
            }
        }
    }
}