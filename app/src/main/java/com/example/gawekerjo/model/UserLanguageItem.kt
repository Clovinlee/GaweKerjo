package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "user_languages")
data class UserLanguageItem(
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    val language_id:Int,
    var created_at:String,
    var updated_at:Any
)
