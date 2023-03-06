package com.rijaldev.mygithub.util

import com.rijaldev.mygithub.data.remote.response.DetailUserResponse
import com.rijaldev.mygithub.data.remote.response.RepoResponse
import com.rijaldev.mygithub.data.remote.response.UserResponse
import com.rijaldev.mygithub.domain.model.DetailUser
import com.rijaldev.mygithub.domain.model.Repo
import com.rijaldev.mygithub.domain.model.User

object DataMapper {

    fun mapUserResponseToDomain(userResponse: UserResponse): User {
        val (username, avatarUrl, type) = userResponse
        return User(
            username = username,
            avatarUrl = avatarUrl,
            type = type,
        )
    }
    
    fun mapDetailUserResponseToDomain(userResponse: DetailUserResponse): DetailUser {
        return DetailUser(
            followers = userResponse.followers,
            avatarUrl = userResponse.avatarUrl,
            following = userResponse.following,
            name = userResponse.name,
            bio = userResponse.bio,
            company = userResponse.company,
            location = userResponse.location,
            publicRepos = userResponse.publicRepos,
            username = userResponse.username,
            type = userResponse.type,
            blog = userResponse.blog,
        )
    }

    fun mapRepoResponseToDomain(repoResponse: RepoResponse): Repo {
        return Repo(
            id = repoResponse.id,
            stargazersCount = repoResponse.stargazersCount,
            topics = repoResponse.topics,
            description = repoResponse.description,
            language = repoResponse.language,
            updatedAt = repoResponse.updatedAt,
            name = repoResponse.name,
            forksCount = repoResponse.forksCount
        )
    }
}