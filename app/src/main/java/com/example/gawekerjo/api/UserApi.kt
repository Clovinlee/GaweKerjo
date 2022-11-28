package com.example.gawekerjo.api

import com.example.gawekerjo.model.UserItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET("users")
    suspend fun getUser(
        @Query("id") id : Int?,
        @Query("email") email : String?,
        @Query("password") password : String?,
        ): Call<List<UserItem>>

    @POST("users")
    suspend fun addUser(
        @Body user : UserItem
    ): Call<UserItem>
}