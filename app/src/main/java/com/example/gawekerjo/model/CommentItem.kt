package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "comments")
data class CommentItem(
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    var body:String,
    var created_at:String,
    var updated_at:Any
)
