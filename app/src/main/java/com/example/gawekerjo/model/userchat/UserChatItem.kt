package com.example.gawekerjo.model.userchat

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_chats")
@Parcelize
data class UserChatItem(
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    val chat_id:Int,
    val message:String,
    var created_at:String,
    var updated_at:String?,
):Parcelable
