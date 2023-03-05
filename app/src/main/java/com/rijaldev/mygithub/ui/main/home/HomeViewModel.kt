package com.rijaldev.mygithub.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.domain.model.User
import com.rijaldev.mygithub.domain.repository.UserRepository
import com.rijaldev.mygithub.util.Event
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _users = MutableLiveData<List<User>?>()
    val users: LiveData<List<User>?> = _users

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val queryChannel = MutableStateFlow("")

    init {
        getDefaultUsers()
    }

    private fun getDefaultUsers() = viewModelScope.launch {
        userRepository.getUsers()
            .collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                    }
                    is Result.Success -> {
                        _isLoading.value = false
                        _users.value = result.data
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = Event(result.message)
                    }
                }
            }
    }

    @OptIn(FlowPreview::class)
    fun searchUsers(query: String) = viewModelScope.launch {
        queryChannel.value = query
        userRepository.searchUsers(queryChannel.value)
            .collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                    }
                    is Result.Success -> {
                        _isLoading.value = false
                        _users.value = result.data
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = Event(result.message)
                    }
                }
            }
    }

    fun retry(isForSearch: Boolean) {
        if (isForSearch) searchUsers(queryChannel.value)
        else getDefaultUsers()
    }
}