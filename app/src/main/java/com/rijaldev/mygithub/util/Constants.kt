package com.rijaldev.mygithub.util

import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
    val DARK_MODE = booleanPreferencesKey("is_dark_mode_active")
    const val SETTING_PREFERENCES = "setting_preferences"
    const val TYPE_USER = "User"
    const val TYPE_ORGANIZATION = "Organization"
}