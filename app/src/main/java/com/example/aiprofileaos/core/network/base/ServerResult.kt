package com.example.aiprofileaos.core.network.base

interface ServerResult {
    fun isSuccess(): Boolean    //200ok이면서 resultCode가 0인 경우
    fun resultCode(): String
    fun errorMessage(): String?
}