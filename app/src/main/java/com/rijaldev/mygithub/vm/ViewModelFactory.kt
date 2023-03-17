package com.rijaldev.mygithub.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rijaldev.mygithub.di.RepositoryModule
import com.rijaldev.mygithub.domain.repository.FavoriteUserRepository
import com.rijaldev.mygithub.domain.repository.SettingRepository
import com.rijaldev.mygithub.domain.repository.UserRepository
import com.rijaldev.mygithub.presentation.detail.DetailViewModel
import com.rijaldev.mygithub.presentation.detail.follow.FollowViewModel
import com.rijaldev.mygithub.presentation.detail.repo.RepoViewModel
import com.rijaldev.mygithub.presentation.main.MainViewModel
import com.rijaldev.mygithub.presentation.main.favorite.FavoriteViewModel
import com.rijaldev.mygithub.presentation.main.home.HomeViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val favoriteUserRepository: FavoriteUserRepository,
    private val settingRepository: SettingRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(settingRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(favoriteUserRepository) as T
            }
            modelClass.isAssignableFrom(RepoViewModel::class.java) -> {
                RepoViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(userRepository, favoriteUserRepository) as T
            }
            modelClass.isAssignableFrom(FollowViewModel::class.java) -> {
                FollowViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
        }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    userRepository = RepositoryModule.provideUserRepository(),
                    favoriteUserRepository = RepositoryModule.provideFavoriteUserRepository(context),
                    settingRepository = RepositoryModule.provideSettingRepository(context)
                )
            }.also {
                instance = it
            }
    }
}