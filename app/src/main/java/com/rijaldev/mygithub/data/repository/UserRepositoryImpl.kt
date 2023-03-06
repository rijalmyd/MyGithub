package com.rijaldev.mygithub.data.repository

import androidx.lifecycle.liveData
import com.rijaldev.mygithub.data.remote.RemoteDataSource
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.domain.repository.UserRepository
import com.rijaldev.mygithub.util.DataMapper

class UserRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : UserRepository {

    override fun searchUsers(query: String) = liveData {
        emit(Result.Loading())
        try {
            val response = remoteDataSource.searchUsers(query)
            val result = response.items.map { userResponse ->
                DataMapper.mapUserResponseToDomain(userResponse)
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    override fun getDetailUser(username: String) = liveData {
        emit(Result.Loading())
        try {
            val response = remoteDataSource.getDetailUser(username)
            val result = DataMapper.mapDetailUserResponseToDomain(response)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    override fun getRepos(username: String) = liveData {
        emit(Result.Loading())
        try {
            val response = remoteDataSource.getRepos(username)
            val result = response.map { repoResponse ->
                DataMapper.mapRepoResponseToDomain(repoResponse)
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    override fun getFollowers(username: String) = liveData {
        emit(Result.Loading())
        try {
            val response = remoteDataSource.getFollowers(username)
            val result = response.map { userResponse ->
                DataMapper.mapUserResponseToDomain(userResponse)
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    override fun getFollowing(username: String) = liveData {
        emit(Result.Loading())
        try {
            val response = remoteDataSource.getFollowing(username)
            val result = response.map { userResponse ->
                DataMapper.mapUserResponseToDomain(userResponse)
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepositoryImpl? = null

        fun getInstance(remoteDataSource: RemoteDataSource) =
            instance ?: synchronized(this) {
                instance ?: UserRepositoryImpl(remoteDataSource)
            }.also {
                instance = it
            }
    }
}