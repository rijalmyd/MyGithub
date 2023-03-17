package com.rijaldev.mygithub.util

import com.rijaldev.mygithub.domain.model.User

object DummyTest {
    const val isDarkModeActiveFalseDummy = false
    const val isDarkModeActiveTrueDummy = true
    const val dummyQuery = "Dicoding"

    fun generateDummySearchUsers(): List<User> {
        return listOf(
            User("userA", "https://a.png", "User"),
            User("userB", "https://b.png", "User"),
            User("userC", "https://c.png", "Organization"),
            User("userD", "https://d.png", "User"),
            User("userE", "https://e.png", "User"),
        )
    }

    fun generateErrorMessage() = "Oops, something went wrong!"
}