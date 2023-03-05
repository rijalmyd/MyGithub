package com.rijaldev.mygithub.data.remote.retrofit

import com.rijaldev.mygithub.data.remote.response.DetailUserResponse
import com.rijaldev.mygithub.data.remote.response.SearchUserResponse
import com.rijaldev.mygithub.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<UserResponse>

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): SearchUserResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): List<UserResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): List<UserResponse>
}