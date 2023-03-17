package com.rijaldev.mygithub.di

import android.content.Context
import com.rijaldev.mygithub.data.repository.FavoriteUserRepositoryImpl
import com.rijaldev.mygithub.data.repository.SettingRepositoryImpl
import com.rijaldev.mygithub.data.repository.UserRepositoryImpl
import com.rijaldev.mygithub.domain.repository.FavoriteUserRepository
import com.rijaldev.mygithub.domain.repository.SettingRepository
import com.rijaldev.mygithub.domain.repository.UserRepository

object RepositoryModule {

    fun provideUserRepository(): UserRepository {
        val remoteDataSource = NetworkModule.provideRemoteDataSource()
        return UserRepositoryImpl.getInstance(remoteDataSource)
    }

    fun provideSettingRepository(context: Context): SettingRepository {
        val localDataSource = DatabaseModule.provideLocalDataSource(context)
        return SettingRepositoryImpl.getInstance(localDataSource)
    }

    fun provideFavoriteUserRepository(context: Context): FavoriteUserRepository {
        val localDataSource = DatabaseModule.provideLocalDataSource(context)
        return FavoriteUserRepositoryImpl.getInstance(localDataSource)
    }
}