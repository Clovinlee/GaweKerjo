package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "user_chats")
data class UserChatItem(
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    val chat_id:Int,
    val message:String,
    var created_at:String,
    var updated_at:String?,
)
