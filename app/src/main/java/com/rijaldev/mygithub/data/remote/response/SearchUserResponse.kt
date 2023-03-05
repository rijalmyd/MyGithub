package com.rijaldev.mygithub.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val items: List<UserResponse>
)