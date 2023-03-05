package com.rijaldev.mygithub.domain.repository

import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.domain.model.DetailUser
import com.rijaldev.mygithub.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(): Flow<Result<List<User>>>

    fun searchUsers(query: String): Flow<Result<List<User>>>

    fun getDetailUser(username: String): Flow<Result<DetailUser>>

    fun getFollowers(username: String): Flow<Result<List<User>>>

    fun getFollowing(username: String): Flow<Result<List<User>>>
}