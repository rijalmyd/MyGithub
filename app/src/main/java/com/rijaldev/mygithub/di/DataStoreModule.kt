package com.rijaldev.mygithub.di

import android.content.Context
import com.rijaldev.mygithub.data.local.datastore.SettingPreferences
import com.rijaldev.mygithub.data.local.datastore.SettingPreferencesImpl

object DataStoreModule {

    fun provideSettingPreferences(context: Context): SettingPreferences {
        return SettingPreferencesImpl.getInstance(context)
    }
}