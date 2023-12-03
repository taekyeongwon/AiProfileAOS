package com.example.aiprofileaos.data.dto

import com.example.aiprofileaos.core.network.base.ServerResult

open class BaseResponse(
    var resultCode: Int = 0,
    var desc: String? = null
): ServerResult {

    override fun isSuccess(): Boolean {
        return resultCode == 0
    }

    override fun resultCode(): String {
        return resultCode.toString()
    }

    override fun errorMessage(): String? {
        return desc
    }
}