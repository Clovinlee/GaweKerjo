package com.example.gawekerjo.api

import com.example.gawekerjo.model.userchat.UserChat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserChatApi {
    @GET("userchat")
    fun getDChat(
        @Query("id")id:Int
    ): Call<UserChat>
    @POST("adduserchat")
    fun addChat(
        @Query("user_id")user_id:Int,
        @Query("chat_id")chat_id:Int,
        @Query("message")message:String
    ): Call<UserChat>
    @GET("friendtodchat")
    fun friendtoDchat(
        @Query("chat_id")chat_id:Int
    ): Call<UserChat>
}