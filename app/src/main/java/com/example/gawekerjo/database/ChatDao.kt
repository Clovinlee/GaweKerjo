package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.chat.ChatItem

@Dao
interface ChatDao {
    @Insert
    suspend fun insertChat(chat: ChatItem)
    @Delete
    suspend fun deleteChat(chat: ChatItem)
    @Update
    suspend fun updateChat(chat: ChatItem)
    @Query("SELECT * FROM chats WHERE user_id=:id OR recipient_id=:id")
    suspend fun getAllChat(id:Int):List<ChatItem>
    @Query("DELETE FROM chats WHERE user_id=:id OR recipient_id=:id")
    suspend fun clear(id:Int)
}