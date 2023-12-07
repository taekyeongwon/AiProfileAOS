package com.example.aiprofileaos.data.dto

data class GithubRepos(val total_count: Int, val incomplete_results: Boolean, val items: List<GithubRepo>?): BaseResponse()
data class GithubRepo(val id: Long, val node_id: String, val name: String)
