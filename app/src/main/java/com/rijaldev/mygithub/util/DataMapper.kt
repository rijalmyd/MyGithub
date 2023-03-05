package com.rijaldev.mygithub.util

import com.rijaldev.mygithub.data.remote.response.DetailUserResponse
import com.rijaldev.mygithub.data.remote.response.UserResponse
import com.rijaldev.mygithub.domain.model.DetailUser
import com.rijaldev.mygithub.domain.model.User

object DataMapper {

    fun mapUserResponseToDomain(userResponse: UserResponse): User {
        val (username, avatarUrl, type) = userResponse
        return User(
            username = username,
            avatarUrl = avatarUrl,
            type = type
        )
    }
    
    fun mapDetailUserResponseToEntity(detailUserResponse: DetailUserResponse): DetailUser {
        val (
            followers,
            avatarUrl,
            following,
            name,
            bio,
            company,
            location,
            publicRepos,
            username,
            type,
            blog
        ) = detailUserResponse

        return DetailUser(
            followers = followers,
            avatarUrl = avatarUrl,
            following = following,
            name = name,
            bio = bio,
            company = company,
            location = location,
            publicRepos = publicRepos,
            username = username,
            type = type,
            blog = blog
        )
    }
}