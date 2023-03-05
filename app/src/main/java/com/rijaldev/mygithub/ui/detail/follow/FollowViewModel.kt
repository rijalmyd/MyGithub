package com.rijaldev.mygithub.ui.detail.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.domain.model.User
import com.rijaldev.mygithub.domain.repository.UserRepository
import com.rijaldev.mygithub.util.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FollowViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _username = MutableLiveData<String>()

    fun setUsername(username: String) {
        _username.value = username
    }

    val userFollowers by lazy {
        Transformations.switchMap(_username) { username ->
            userRepository.getFollowers(username).asLiveData()
        }
    }

    val userFollowing by lazy {
        Transformations.switchMap(_username) { username ->
            userRepository.getFollowing(username).asLiveData()
        }
    }
}