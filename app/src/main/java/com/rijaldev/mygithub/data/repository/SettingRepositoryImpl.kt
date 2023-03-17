package com.rijaldev.mygithub.data.repository

import com.rijaldev.mygithub.data.local.LocalDataSource
import com.rijaldev.mygithub.domain.repository.SettingRepository

class SettingRepositoryImpl(
    private val localDataSource: LocalDataSource
) : SettingRepository {

    override fun getTheme() = localDataSource.getTheme()

    override suspend fun setTheme(isDarkModeActive: Boolean) = localDataSource.setTheme(isDarkModeActive)

    companion object {
        @Volatile
        private var instance: SettingRepositoryImpl? = null

        fun getInstance(localDataSource: LocalDataSource) =
            instance ?: synchronized(this) {
                instance ?: SettingRepositoryImpl(localDataSource)
            }.also {
                instance = it
            }
    }
}