package com.rijaldev.mygithub.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.domain.model.User
import com.rijaldev.mygithub.domain.repository.UserRepository
import com.rijaldev.mygithub.util.Event
import com.rijaldev.mygithub.util.LiveDataSource
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
    private val _query = MutableLiveData<String>()
    private val fetchSearchUsers = MutableLiveData<Unit>()

    fun fetchSearchUsers(query: String) {
        fetchSearchUsers.value = Unit
        _query.value = query
    }

    val searchUsers by lazy {
        Transformations.switchMap(LiveDataSource(_query, fetchSearchUsers)) {
            userRepository.searchUsers(it.first.toString()).asLiveData()
        }
    }
}