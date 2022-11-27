package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "post_comments")
data class PostCommentItem(
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    val post_id:Int,
    var created_at:String,
    var updated_at:String?,
)
