package com.rijaldev.mygithub.data.repository

import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.rijaldev.mygithub.data.local.LocalDataSource
import com.rijaldev.mygithub.domain.model.User
import com.rijaldev.mygithub.domain.repository.FavoriteUserRepository
import com.rijaldev.mygithub.util.DataMapper

class FavoriteUserRepositoryImpl(
    private val localDataSource: LocalDataSource
) : FavoriteUserRepository {

    override suspend fun insert(user: User) {
        val userEntity = DataMapper.mapUserDomainToEntity(user)
        localDataSource.insert(userEntity)
    }

    override suspend fun delete(username: String) =
        localDataSource.delete(username)

    override fun isFavoriteUser(username: String) =
        localDataSource.isFavoriteUser(username)

    override fun getFavoriteUsers() = liveData {
        val users = localDataSource.getFavoriteUsers().map { users ->
            users.map { userEntity ->
                DataMapper.mapUserEntityToDomain(userEntity)
            }
        }
        emitSource(users)
    }

    companion object {
        @Volatile
        private var instance: FavoriteUserRepositoryImpl? = null

        fun getInstance(localDataSource: LocalDataSource) =
            instance ?: synchronized(this) {
                instance ?: FavoriteUserRepositoryImpl(localDataSource)
            }.also {
                instance = it
            }
    }
}