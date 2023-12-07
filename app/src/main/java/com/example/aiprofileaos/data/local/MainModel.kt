package com.example.aiprofileaos.data.local

import com.example.aiprofileaos.core.network.MainApiServer
import com.example.aiprofileaos.core.network.base.NetResult
import com.example.aiprofileaos.data.dto.GithubRepos

class MainModel {
    suspend fun getRepos(query: String): NetResult<GithubRepos> {
        return MainApiServer.parsingCoroutine {
            MainApiServer.API.getRepositories(query)
        }
    }
}