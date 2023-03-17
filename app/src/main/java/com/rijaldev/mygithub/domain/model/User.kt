package com.rijaldev.mygithub.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String?,
    val avatarUrl: String?,
    val type: String?,
    val id: Int = 0,
) : Parcelable
