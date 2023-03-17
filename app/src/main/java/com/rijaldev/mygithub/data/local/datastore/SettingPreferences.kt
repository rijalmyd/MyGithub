package com.rijaldev.mygithub.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface SettingPreferences {

    fun getTheme(): Flow<Boolean>

    suspend fun setTheme(isDarkModeActive: Boolean)
}