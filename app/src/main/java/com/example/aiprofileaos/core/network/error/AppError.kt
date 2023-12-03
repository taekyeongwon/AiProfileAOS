package com.example.aiprofileaos.core.network.error

import android.content.Context

object AppError {
    open class Base(message: String? = "", cause: Throwable? = null): Exception(message, cause) {
        open fun getMessage(context: Context): String {
            return "에러 발생"
        }

        open fun getResultCode(): String {
            return "-1"
        }
    }
}