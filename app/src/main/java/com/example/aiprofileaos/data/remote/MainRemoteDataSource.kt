package com.example.aiprofileaos.data.remote

import com.example.aiprofileaos.BuildConfig
import com.example.aiprofileaos.core.network.base.BaseNetwork
import com.example.aiprofileaos.core.network.base.ApiResult
import com.example.aiprofileaos.core.network.base.ServerResult
import com.example.aiprofileaos.core.network.error.AppError
import com.example.aiprofileaos.data.MainDataSource
import com.example.aiprofileaos.data.model.GithubRepos
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.AssertionError

class MainRemoteDataSource: BaseNetwork<MainDao>(), MainDataSource {
    override fun createNetwork(): MainDao {
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
            .create(MainDao::class.java)
    }

    override suspend fun getRepositories(query: String): ApiResult<GithubRepos> {
        return callApi { api.getRepositories(query) }
    }

    //람다식 실행 결과를 Any로 업캐스팅해서 받음. 라이브러리마다 필요한 응답 객체로 다운캐스팅해서 사용하기 위함.
    // ex) Retrofit의 경우 Response, Call 객체로 반환받음.(status code 받기 위해, 필요 없는 경우 직접 DTO객체로 받아도 됨)
    @Suppress("UNCHECKED_CAST")
    private suspend fun <S : ServerResult> callApi(call: suspend () -> Any): ApiResult<S> {
        val response = call() as? Response<S> ?: throw AssertionError("dao응답은 Response 객체로 반환 해야 함.")
        return try {
            if (response.isSuccessful) {  //200 ok
                val base = response.body()!!
                if (base.isSuccess()) ApiResult.Success(base) //정상 응답
                else ApiResult.Error(AppError.Server(base))             //resultCode가 0이 아님
            } else ApiResult.Error(AppError.Network(response.code()))   //200 외 서버 통신 에러
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResult.Error(AppError.Network(-1))    //네트워크 통신 실패, 인터넷 연결x 등 에러 처리
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