package com.example.aiprofileaos.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.aiprofileaos.data.local.MainModel
import com.example.aiprofileaos.ui.base.BaseViewModel

const val COUNT_STATE_KEY = "COUNT_STATE_KEY"
class MainViewModel(
    private val model: MainModel,
    private val savedStateHandle: SavedStateHandle
    ): BaseViewModel() {  //추후 local datasource 접근하는 코드 추가 시 Repository 패턴으로 변경.

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> get() = _count

    init {
        Log.d("test", "init")
        _count.value = savedStateHandle[COUNT_STATE_KEY] ?: 1
    }

    fun addCount() {
        _count.value = _count.value?.plus(1)
        savedStateHandle[COUNT_STATE_KEY] = _count.value
    }
}