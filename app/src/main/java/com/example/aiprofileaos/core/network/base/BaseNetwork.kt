package com.example.aiprofileaos.core.network.base

import retrofit2.Call

abstract class BaseNetwork<T> {
    protected var networkLibrary: T

    init {
        networkLibrary = createNetwork()
    }

    abstract fun createNetwork(): T
    abstract fun <V: ServerResult> parsingResponse(call: Call<V>, callback: NetResultCallback<V>)   //콜백으로 응답 처리하는 경우 재정의
    abstract suspend fun <V: ServerResult> parsingCoroutine(call: suspend () -> Any): NetResult<V>  //코루틴으로 응답 처리하는 경우 재정의
}