package com.example.gawekerjo.api

import com.example.gawekerjo.model.chat.Chat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatApi {
    @GET("chats")
    fun getChat(
        @Query("user_id")user_id:Int
    ): Call<Chat>
}