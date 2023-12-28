package com.example.aiprofileaos.data

import com.example.aiprofileaos.core.network.base.ApiResult
import com.example.aiprofileaos.data.model.GithubRepos

interface MainDataSource {
    suspend fun getRepositories(query: String): ApiResult<GithubRepos>
}