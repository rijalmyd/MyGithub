package com.rijaldev.mygithub.presentation.detail.follow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rijaldev.mygithub.domain.repository.UserRepository

class FollowViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _username = MutableLiveData<String>()

    fun setUsername(username: String) {
        _username.value = username
    }

    val userFollowers by lazy {
        Transformations.switchMap(_username) { username ->
            userRepository.getFollowers(username)
        }
    }

    val userFollowing by lazy {
        Transformations.switchMap(_username) { username ->
            userRepository.getFollowing(username)
        }
    }
}