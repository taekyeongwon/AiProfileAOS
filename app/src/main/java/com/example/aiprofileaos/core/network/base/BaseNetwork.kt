package com.example.aiprofileaos.core.network.base

import com.example.aiprofileaos.core.network.MainApiProtocol
import retrofit2.Call
import retrofit2.Response

abstract class BaseNetwork<T> {
    protected var networkLibrary: T? = null

    fun initialize() {
        networkLibrary = createNetwork()
    }

    abstract fun createNetwork(): T
    open fun <V: ServerResult> parsingResponse(call: Call<V>, callback: NetResultCallback<V>) {}   //콜백으로 응답 처리하는 경우 재정의
    open suspend fun <V: ServerResult> parsingCoroutine(call: suspend () -> Any): NetResult<V> {  //코루틴으로 응답 처리하는 경우 재정의
        return NetResult.Success(data = null)
    }
}