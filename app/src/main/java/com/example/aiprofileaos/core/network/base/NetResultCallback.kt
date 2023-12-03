package com.example.aiprofileaos.core.network.base

interface NetResultCallback<T: ServerResult> {  //콜백으로 Result 데이터 클래스 받기 위한 인터페이스
    fun onResponse(response: NetResult<T>)
}