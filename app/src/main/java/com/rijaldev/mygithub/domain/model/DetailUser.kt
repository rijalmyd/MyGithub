package com.rijaldev.mygithub.domain.model

data class DetailUser(
    val followers: Int?,
    val avatarUrl: String?,
    val following: Int?,
    val name: String?,
    val bio: String?,
    val company: String?,
    val location: String?,
    val publicRepos: Int?,
    val username: String?,
    val type: String?,
    val blog: String?,
)
