package com.example.aiprofileaos.data.remote

import com.example.aiprofileaos.core.network.base.ApiResult
import com.example.aiprofileaos.data.model.GithubRepos

class MainRepository(private val mainDataSource: MainRemoteDataSource) {
    suspend fun getRepos(query: String): ApiResult<GithubRepos> {
        return mainDataSource.getRepositories(query)
    }
}