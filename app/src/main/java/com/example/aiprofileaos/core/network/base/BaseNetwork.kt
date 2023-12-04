package com.example.aiprofileaos.core.network.base

import retrofit2.Call

abstract class BaseNetwork<T> {
    protected var networkLibrary: T? = null

    fun initialize() {
        networkLibrary = createNetwork()
    }

    abstract fun createNetwork(): T
    open fun <V: ServerResult> parsingResponse(call: Call<V>, callback: NetResultCallback<V>) {}   //콜백으로 응답 처리하는 경우 재정의
    open suspend fun <V: ServerResult> parsingCoroutine(call: suspend () -> Any): NetResult<V> {  //코루틴으로 응답 처리하는 경우 재정의
        return NetResult(status = NetStatus.FAIL, data = null, throwable = null)
    }
}