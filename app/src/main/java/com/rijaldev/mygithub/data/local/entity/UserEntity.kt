package com.rijaldev.mygithub.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class UserEntity(

    @ColumnInfo(name = "username")
    val username: String?,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,

    @ColumnInfo(name = "type")
    val type: String?,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
)
