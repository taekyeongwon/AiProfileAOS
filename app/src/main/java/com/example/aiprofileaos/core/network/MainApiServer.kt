package com.example.aiprofileaos.core.network

import com.example.aiprofileaos.BuildConfig
import com.example.aiprofileaos.core.network.base.BaseNetwork
import com.example.aiprofileaos.core.network.base.NetResult
import com.example.aiprofileaos.core.network.base.NetResultCallback
import com.example.aiprofileaos.core.network.base.ServerResult
import com.example.aiprofileaos.core.network.error.AppError
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.AssertionError

object MainApiServer: BaseNetwork<Retrofit>() {
    init {
        initialize()
    }
    val API: MainApiProtocol = networkLibrary!!.create(MainApiProtocol::class.java)

    override fun createNetwork(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().apply {
            writeTimeout(10, TimeUnit.SECONDS)  //default
            if(BuildConfig.DEBUG) {
                addInterceptor(interceptor)
            }
        }.build()

        return Retrofit.Builder()
            .baseUrl("http://api.github.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //람다식 실행 결과를 Any로 업캐스팅해서 받음. 라이브러리마다 필요한 응답 객체로 다운캐스팅해서 사용하기 위함.
    // ex) Retrofit의 경우 Response, Call 객체로 반환받음.
    @Suppress("UNCHECKED_CAST")
    override suspend fun <V : ServerResult> parsingCoroutine(call: suspend () -> Any): NetResult<V> {
        return try {
            val response = call() as? Response<V>
                ?: throw AssertionError("호출된 api의 return type은 반드시 BaseResponse의 자식 클래스여야 함.")
            if (response.isSuccessful) {  //200 ok
                val base = response.body() as ServerResult
                if (base.isSuccess()) NetResult.Success(response.body()) //정상 응답
                else NetResult.Error(AppError.Server(base))             //resultCode가 0이 아님
            } else NetResult.Error(AppError.Network(response.code()))   //200 외 서버 통신 에러
        } catch (e: Exception) {
            e.printStackTrace()
            NetResult.Error(AppError.Network(-1))    //네트워크 통신 실패, 인터넷 연결x 등 에러 처리
        }
    }

//    override fun <V : ServerResult> parsingResponse(call: Call<V>, callback: NetResultCallback<V>) {
//        call.enqueue(object: Callback<V> {
//            override fun onResponse(call: Call<V>, response: Response<V>) {
//                if(response.isSuccessful) {
//                    val base = response.body() as ServerResult
//                    if(base.isSuccess()) callback.onResponse(NetResult.success(response.body()))
//                    else callback.onResponse(NetResult.error(AppError.Server(base)))
//                } else callback.onResponse(NetResult.error(AppError.Network(response.code())))
//            }
//
//            override fun onFailure(call: Call<V>, t: Throwable) {
//                callback.onResponse(NetResult.error(AppError.Network(-1, t)))   //네트워크 통신 실패, 인터넷 연결x 등 에러 처리
//            }
//        })
//    }
}