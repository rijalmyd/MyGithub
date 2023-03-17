package com.rijaldev.mygithub.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    fun getTheme(): Flow<Boolean>

    suspend fun setTheme(isDarkModeActive: Boolean)
}