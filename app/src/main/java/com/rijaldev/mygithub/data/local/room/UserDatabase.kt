package com.rijaldev.mygithub.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rijaldev.mygithub.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_db"
                ).build()
            }.also {
                instance = it
            }
    }
}