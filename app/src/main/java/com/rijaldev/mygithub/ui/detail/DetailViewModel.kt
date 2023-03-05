package com.rijaldev.mygithub.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.domain.model.DetailUser
import com.rijaldev.mygithub.domain.repository.UserRepository
import com.rijaldev.mygithub.util.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _username = MutableLiveData<String>()

    fun setUsername(username: String) {
        _username.value = username
    }

    val detailUser by lazy {
        _username.switchMap { username ->
            userRepository.getDetailUser(username).asLiveData()
        }
    }

}