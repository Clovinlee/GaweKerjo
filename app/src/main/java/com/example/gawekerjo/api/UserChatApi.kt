package com.example.gawekerjo.api

import com.example.gawekerjo.model.userchat.UserChat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserChatApi {
    @GET("userchat")
    fun getDChat(
        @Query("id")id:Int
    ): Call<UserChat>
}