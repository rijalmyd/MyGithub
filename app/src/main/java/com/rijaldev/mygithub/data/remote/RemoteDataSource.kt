package com.rijaldev.mygithub.data.remote

import com.rijaldev.mygithub.data.remote.retrofit.ApiService

class RemoteDataSource(
    private val apiService: ApiService,
) {

    suspend fun getUsers() = apiService.getUsers()

    suspend fun searchUsers(query: String) = apiService.searchUsers(query)

    suspend fun getDetailUser(username: String) = apiService.getDetailUser(username)

    suspend fun getFollowers(username: String) = apiService.getFollowers(username)

    suspend fun getFollowing(username: String) = apiService.getFollowing(username)

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiService)
            }.also {
                instance = it
            }
    }
}