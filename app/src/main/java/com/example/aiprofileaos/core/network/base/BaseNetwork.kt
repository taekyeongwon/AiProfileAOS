package com.example.aiprofileaos.core.network.base


abstract class BaseNetwork<T> {
    protected val api: T by lazy {
        createNetwork()
    }

    abstract fun createNetwork(): T
}