package com.rijaldev.mygithub.domain.model

data class Repo(
    val id: Int?,
    val stargazersCount: Int?,
    val topics: List<String?>?,
    val description: String?,
    val language: String?,
    val updatedAt: String?,
    val name: String?,
    val forksCount: Int?,
)
