package com.rijaldev.mygithub.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rijaldev.mygithub.di.RepositoryModule
import com.rijaldev.mygithub.domain.repository.UserRepository
import com.rijaldev.mygithub.ui.detail.DetailViewModel
import com.rijaldev.mygithub.ui.detail.follow.FollowViewModel
import com.rijaldev.mygithub.ui.main.home.HomeViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(FollowViewModel::class.java) -> {
                FollowViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
        }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    userRepository = RepositoryModule.provideUserRepository()
                )
            }.also {
                instance = it
            }
    }
}