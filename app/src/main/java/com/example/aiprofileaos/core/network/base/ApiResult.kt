package com.example.aiprofileaos.core.network.base

import com.example.aiprofileaos.core.network.error.AppError

sealed class ApiResult<out T: ServerResult> {
    data class Success<T: ServerResult>(val data: T): ApiResult<T>()
    data class Error(val error: AppError.Base?): ApiResult<Nothing>()
}