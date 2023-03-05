package com.rijaldev.mygithub.di

import com.rijaldev.mygithub.data.remote.RemoteDataSource
import com.rijaldev.mygithub.data.repository.UserRepositoryImpl
import com.rijaldev.mygithub.domain.repository.UserRepository

object RepositoryModule {

    fun provideUserRepository(): UserRepository {
        val remoteDataSource = NetworkModule.provideRemoteDataSource()
        return UserRepositoryImpl.getInstance(remoteDataSource)
    }
}