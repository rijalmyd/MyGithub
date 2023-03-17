package com.rijaldev.mygithub.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.rijaldev.mygithub.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = Constants.SETTING_PREFERENCES)

class SettingPreferencesImpl(context: Context) : SettingPreferences {

    private val dataStore = context.dataStore

    override fun getTheme(): Flow<Boolean> =
        dataStore.data.map {
            it[Constants.DARK_MODE] ?: false
        }

    override suspend fun setTheme(isDarkModeActive: Boolean) {
        dataStore.edit {
            it[Constants.DARK_MODE] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var instance: SettingPreferencesImpl? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: SettingPreferencesImpl(context)
            }.also {
                instance = it
            }
    }
}