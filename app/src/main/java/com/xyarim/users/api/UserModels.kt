package com.xyarim.users.api

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: Int,
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("last_name")
    var lastName: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("avatar_url")
    var avatarUrl: String?,
    @SerializedName("created_at")
    var createdAt: String?
)

data class UpdateUserRequest(@SerializedName("user") var user: User)
