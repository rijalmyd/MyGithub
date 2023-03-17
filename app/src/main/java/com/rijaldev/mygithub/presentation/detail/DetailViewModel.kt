package com.rijaldev.mygithub.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.rijaldev.mygithub.domain.model.User
import com.rijaldev.mygithub.domain.repository.FavoriteUserRepository
import com.rijaldev.mygithub.domain.repository.UserRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val userRepository: UserRepository,
    private val favoriteUserRepository: FavoriteUserRepository
) : ViewModel() {

    private val _username = MutableLiveData<String>()

    fun setUsername(username: String) {
        _username.value = username
    }

    val detailUser by lazy {
        _username.switchMap { username ->
            userRepository.getDetailUser(username)
        }
    }

    fun insert(user: User) = viewModelScope.launch {
        favoriteUserRepository.insert(user)
    }

    fun delete(username: String) = viewModelScope.launch {
        favoriteUserRepository.delete(username)
    }

    fun isFavoriteUser(username: String) =
        favoriteUserRepository.isFavoriteUser(username)
}