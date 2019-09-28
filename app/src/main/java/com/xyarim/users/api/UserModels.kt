package com.xyarim.users.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("last_name")
    var lastName: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("avatar_url")
    var avatarUrl: String? = null,
    @SerializedName("created_at")
    var createdAt: String? = null
) : Serializable

data class UpdateUserRequest(@SerializedName("user") var user: User)
