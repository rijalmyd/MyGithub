package com.rijaldev.mygithub.domain.repository

import androidx.lifecycle.LiveData
import com.rijaldev.mygithub.domain.model.User

interface FavoriteUserRepository {

    suspend fun insert(user: User)

    suspend fun delete(username: String)

    fun isFavoriteUser(username: String): LiveData<Boolean>

    fun getFavoriteUsers(): LiveData<List<User>>
}