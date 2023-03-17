package com.rijaldev.mygithub.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rijaldev.mygithub.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UserEntity)

    @Query("DELETE FROM user_entity WHERE username = :username")
    suspend fun delete(username: String)

    @Query("SELECT EXISTS(SELECT * FROM user_entity WHERE username = :username)")
    fun isFavoriteUser(username: String): LiveData<Boolean>

    @Query("SELECT * FROM user_entity ORDER BY id DESC")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>
}