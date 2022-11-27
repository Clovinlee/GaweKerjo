package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "chats")
data class ChatItem(
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    val recipient_id:Int,
    var created_at:String,
    var updated_at:Any
)
