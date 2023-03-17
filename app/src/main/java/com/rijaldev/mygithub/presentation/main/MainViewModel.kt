package com.rijaldev.mygithub.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rijaldev.mygithub.domain.repository.SettingRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val settingRepository: SettingRepository
) : ViewModel() {

    fun getTheme() = settingRepository.getTheme().asLiveData()

    fun setTheme(isDarkModeActive: Boolean) = viewModelScope.launch {
        settingRepository.setTheme(isDarkModeActive)
    }
}