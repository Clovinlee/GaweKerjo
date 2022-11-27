package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.UserChatItem

@Dao
interface UserChatDao {
    @Insert
    suspend fun insertUserChat(userchat: UserChatItem)
    @Delete
    suspend fun deleteUserChat(userchat: UserChatItem)
    @Update
    suspend fun updateUserChat(userchat: UserChatItem)
    @Query("SELECT * FROM user_chats")
    suspend fun getAllUserChat():List<UserChatItem>
}