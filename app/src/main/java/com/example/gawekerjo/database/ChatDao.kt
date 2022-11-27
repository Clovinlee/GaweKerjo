package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.ChatItem

@Dao
interface ChatDao {
    @Insert
    suspend fun insertChat(chat: ChatItem)
    @Delete
    suspend fun deleteChat(chat: ChatItem)
    @Update
    suspend fun updateChat(chat: ChatItem)
    @Query("SELECT * FROM chats")
    suspend fun getAllChat():List<ChatItem>
}