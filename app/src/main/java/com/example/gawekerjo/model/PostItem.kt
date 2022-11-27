package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostItem (
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    var title:String,
    var body:String,
    var like_count:Int,
    val created_at: String,
    var updated_at: Any
)