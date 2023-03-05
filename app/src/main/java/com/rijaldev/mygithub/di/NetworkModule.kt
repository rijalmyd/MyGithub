package com.rijaldev.mygithub.di

import com.rijaldev.mygithub.data.remote.RemoteDataSource
import com.rijaldev.mygithub.data.remote.retrofit.ApiConfig

object NetworkModule {

    fun provideRemoteDataSource(): RemoteDataSource {
        val apiService = ApiConfig.getApiService()
        return RemoteDataSource.getInstance(apiService)
    }
}