package com.rijaldev.mygithub.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.rijaldev.mygithub.domain.repository.UserRepository

class DetailViewModel(
    private val userRepository: UserRepository
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

}