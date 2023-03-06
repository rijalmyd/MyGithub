package com.rijaldev.mygithub.data.remote.response

import com.google.gson.annotations.SerializedName

data class RepoResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("stargazers_count")
	val stargazersCount: Int? = null,

	@field:SerializedName("topics")
	val topics: List<String?>? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("forks_count")
	val forksCount: Int? = null
)
