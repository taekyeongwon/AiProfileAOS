package com.example.aiprofileaos.data.remote

import com.example.aiprofileaos.data.model.GithubRepos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * POST @Body
 * PUT @Body
 * GET @Query
 * DELETE @Query
 *
 * @Multipart @Part part: MultipartBody.Part
 */
interface MainDao {
    @GET("search/repositories")
    suspend fun getRepositories(@Query("q") query: String): Response<GithubRepos>
}