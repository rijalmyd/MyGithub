package com.rijaldev.mygithub.presentation.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.rijaldev.mygithub.domain.repository.UserRepository
import com.rijaldev.mygithub.util.LiveDataSource

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _query = MutableLiveData<String>()
    private val fetchSearchUsers = MutableLiveData<Unit>()

    fun fetchSearchUsers(query: String) {
        fetchSearchUsers.value = Unit
        _query.value = query
    }

    val searchUsers by lazy {
        LiveDataSource(_query, fetchSearchUsers).switchMap {
            userRepository.searchUsers(it.first.toString())
        }
    }
}