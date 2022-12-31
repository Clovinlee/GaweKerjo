package com.example.gawekerjo.api

import com.example.gawekerjo.model.chat.Chat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatApi {
    @GET("chats")
    fun getChat(
        @Query("user_id")user_id:Int
    ): Call<Chat>
    @POST("addchat")
    fun newChat(
        @Query("user_id")user_id:Int,
        @Query("recipient_id")recipient_id:Int
    ): Call<Chat>
}