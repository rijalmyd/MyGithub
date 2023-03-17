package com.rijaldev.mygithub.data.local

import com.rijaldev.mygithub.data.local.datastore.SettingPreferences
import com.rijaldev.mygithub.data.local.entity.UserEntity
import com.rijaldev.mygithub.data.local.room.UserDao

class LocalDataSource(
    private val settingPreferences: SettingPreferences,
    private val userDao: UserDao,
) {

    fun getTheme() = settingPreferences.getTheme()

    suspend fun setTheme(isDarkModeActive: Boolean) = settingPreferences.setTheme(isDarkModeActive)

    suspend fun insert(user: UserEntity) = userDao.insert(user)

    suspend fun delete(username: String) = userDao.delete(username)

    fun isFavoriteUser(username: String) = userDao.isFavoriteUser(username)

    fun getFavoriteUsers() = userDao.getFavoriteUsers()

    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(settingPreferences: SettingPreferences, userDao: UserDao) =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(settingPreferences, userDao)
            }.also {
                instance = it
            }
    }
}