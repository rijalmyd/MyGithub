package com.rijaldev.mygithub.domain.repository

import androidx.lifecycle.LiveData
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.domain.model.DetailUser
import com.rijaldev.mygithub.domain.model.Repo
import com.rijaldev.mygithub.domain.model.User

interface UserRepository {

    fun searchUsers(query: String): LiveData<Result<List<User>>>

    fun getDetailUser(username: String): LiveData<Result<DetailUser>>

    fun getRepos(username: String): LiveData<Result<List<Repo>>>

    fun getFollowers(username: String): LiveData<Result<List<User>>>

    fun getFollowing(username: String): LiveData<Result<List<User>>>
}