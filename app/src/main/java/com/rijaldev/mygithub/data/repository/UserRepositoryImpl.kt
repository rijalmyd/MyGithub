package com.rijaldev.mygithub.data.repository

import android.provider.ContactsContract.Data
import com.rijaldev.mygithub.data.remote.RemoteDataSource
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.domain.model.DetailUser
import com.rijaldev.mygithub.domain.model.User
import com.rijaldev.mygithub.domain.repository.UserRepository
import com.rijaldev.mygithub.util.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
) : UserRepository {

    override fun getUsers() = flow {
        emit(Result.Loading())
        try {
            val response = remoteDataSource.getUsers()
            val result = response.map { userResponse ->
                DataMapper.mapUserResponseToDomain(userResponse)
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchUsers(query: String) = flow {
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
    }.flowOn(Dispatchers.IO)

    override fun getDetailUser(username: String) = flow {
        emit(Result.Loading())
        try {
            val response = remoteDataSource.getDetailUser(username)
            val result = DataMapper.mapDetailUserResponseToEntity(response)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun getFollowers(username: String) = flow {
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
    }.flowOn(Dispatchers.IO)

    override fun getFollowing(username: String) = flow {
        try {
            val response = remoteDataSource.getFollowing(username)
            val result = response.map { userResponse ->
                DataMapper.mapUserResponseToDomain(userResponse)
            }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

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