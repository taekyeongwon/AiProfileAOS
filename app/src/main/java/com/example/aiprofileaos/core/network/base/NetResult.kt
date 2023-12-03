package com.example.aiprofileaos.core.network.base

import com.example.aiprofileaos.core.network.error.AppError

data class NetResult<T: ServerResult>(val status: NetStatus, val data: T?, val throwable: AppError.Base?) {
    companion object {
        fun <T: ServerResult> success(data: T?): NetResult<T> =
            NetResult(status = NetStatus.SUCCESS, data = data, throwable = null)
        fun <T: ServerResult> error(error: AppError.Base): NetResult<T> =
            NetResult(status = NetStatus.FAIL, data = null, throwable = error)
    }
}
