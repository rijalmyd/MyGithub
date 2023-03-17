package com.rijaldev.mygithub.presentation.detail.follow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.rijaldev.mygithub.domain.repository.UserRepository

class FollowViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _username = MutableLiveData<String>()

    fun setUsername(username: String) {
        _username.value = username
    }

    val userFollowers by lazy {
        _username.switchMap { username ->
            userRepository.getFollowers(username)
        }
    }

    val userFollowing by lazy {
        _username.switchMap { username ->
            userRepository.getFollowing(username)
        }
    }
}