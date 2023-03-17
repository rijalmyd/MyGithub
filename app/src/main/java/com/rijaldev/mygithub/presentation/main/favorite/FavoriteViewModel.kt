package com.rijaldev.mygithub.presentation.main.favorite

import androidx.lifecycle.ViewModel
import com.rijaldev.mygithub.domain.repository.FavoriteUserRepository

class FavoriteViewModel(
    private val favoriteUserRepository: FavoriteUserRepository
) : ViewModel() {

    val favoriteUsers by lazy {
        favoriteUserRepository.getFavoriteUsers()
    }
}