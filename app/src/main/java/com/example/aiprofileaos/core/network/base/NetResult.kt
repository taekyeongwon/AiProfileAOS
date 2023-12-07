package com.example.aiprofileaos.core.network.base

import com.example.aiprofileaos.core.network.error.AppError

sealed class NetResult<T: ServerResult>(val data: T? = null, val throwable: AppError.Base? = null) {
    class Success<T: ServerResult>(data: T?): NetResult<T>(data)
    class Error<T: ServerResult>(error: AppError.Base?): NetResult<T>(null, error)
    class Loading<T: ServerResult> : NetResult<T>()
}
