package com.example.aiprofileaos.core.network.error

import android.content.Context
import com.example.aiprofileaos.R
import com.example.aiprofileaos.core.network.base.ServerResult

object AppError {
    open class Base(message: String? = "", cause: Throwable? = null): Exception(message, cause) {
        open fun getMessage(context: Context): String {
            return "에러 발생"
        }

        open fun getResultCode(): String {
            return "-1"
        }
    }

    /**
     * status code 200 외의 값인 경우 처리(300 ~ 500) 메세지 정의 필요.
     */
    class Network(val httpCode: Int, cause: Throwable? = null): Base("", cause) {
        private val prefix = "net_network_"

        override fun getMessage(context: Context): String {
            if(httpCode == -1) {
                return cause?.message ?: "서버 통신 실패"
            }
            try {
                val ref = R.string::class.java
                val field = ref.getField(prefix + httpCode)
                val resId = field.getInt(null)
                return context.getString(resId)
            } catch (e: Exception) {
                return "서버 통신 실패"
            }
        }
    }

    /**
     * 200ok 이면서 resultCode가 0이 아닌 경우 처리
     */
    class Server(val response: ServerResult, cause: Throwable? = null): Base("", cause) {
        override fun getMessage(context: Context): String {
            return response.errorMessage() ?: "서버 통신 실패"
        }

        override fun getResultCode(): String {
            return response.resultCode()
        }
    }
}