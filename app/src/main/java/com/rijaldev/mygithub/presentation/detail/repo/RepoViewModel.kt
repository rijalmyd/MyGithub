package com.rijaldev.mygithub.presentation.detail.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.rijaldev.mygithub.domain.repository.UserRepository

class RepoViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _username = MutableLiveData<String>()

    fun setUsername(username: String) {
        _username.value = username
    }

    val repos by lazy {
        _username.switchMap { username ->
            userRepository.getRepos(username)
        }
    }
}