package com.example.gawekerjo.model.chat

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "chats")
@Parcelize
data class ChatItem(
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    val recipient_id:Int,
    var created_at:String,
    var updated_at:String?,
    ):Parcelable
