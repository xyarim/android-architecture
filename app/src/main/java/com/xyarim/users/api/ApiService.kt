package com.xyarim.users.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("users.json")
    suspend fun getUsersAsync(): Response<ArrayList<User>>

    @PATCH("/users/{userId}.json")
    suspend fun updateUserAsync(@Path("userId") userId: Int, @Body updateUserRequest: UpdateUserRequest)
            : Response<User>

    @POST("/users.json")
    suspend fun createUserAsync(@Body updateUserRequest: UpdateUserRequest)
            : Response<User>

}