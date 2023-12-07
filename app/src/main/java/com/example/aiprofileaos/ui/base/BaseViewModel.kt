package com.example.aiprofileaos.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel: ViewModel() { //onClear에서 한번에 해제 시켜 주는 경우 사용
    //rx single 사용 시 subscribe()로 리턴된 Disposable 객체를 라이프사이클에 맞춰 종료될 때 한 번에 해제시켜 주기 위한 객체(메모리 누수 방지)
    private val compositeDisposable = CompositeDisposable()

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    protected val ioDispatchers = Dispatchers.IO + coroutineExceptionHandler
    protected val uiDispatchers = Dispatchers.Main + coroutineExceptionHandler

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}