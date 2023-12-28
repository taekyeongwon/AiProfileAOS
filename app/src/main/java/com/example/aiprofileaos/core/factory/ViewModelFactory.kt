package com.example.aiprofileaos.core.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.aiprofileaos.data.remote.MainRemoteDataSource
import com.example.aiprofileaos.data.remote.MainRepository
import com.example.aiprofileaos.ui.main.MainViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
val ViewModelFactory = object: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val handle = extras.createSavedStateHandle()
        return when(modelClass) {
            MainViewModel::class.java -> MainViewModel(MainRepository(MainRemoteDataSource()), handle)
            else -> throw IllegalArgumentException("Unknown Class")
        } as T
    }
}