package com.xyarim.users.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("users.json")
    fun getUsersAsync(): Deferred<Response<ArrayList<User>>>

    @PATCH("/users/{userId}.json")
    fun updateUserAsync(@Path("userId") userId: Int, @Body updateUserRequest: UpdateUserRequest)
            : Deferred<Response<User>>

    @POST("/users.json")
    fun createUserAsync(@Body updateUserRequest: UpdateUserRequest)
            : Deferred<Response<User>>

}