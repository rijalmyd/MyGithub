package com.rijaldev.mygithub.di

import android.content.Context
import com.rijaldev.mygithub.data.local.LocalDataSource
import com.rijaldev.mygithub.data.local.room.UserDao
import com.rijaldev.mygithub.data.local.room.UserDatabase

object DatabaseModule {

    private fun provideUserDao(context: Context): UserDao {
        val userDatabase = UserDatabase.getInstance(context)
        return userDatabase.getUserDao()
    }

    fun provideLocalDataSource(context: Context): LocalDataSource {
        val userDao = provideUserDao(context)
        val settingPreferences = DataStoreModule.provideSettingPreferences(context)
        return LocalDataSource.getInstance(settingPreferences, userDao)
    }
}