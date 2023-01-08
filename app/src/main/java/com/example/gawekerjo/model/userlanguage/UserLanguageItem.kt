package com.example.gawekerjo.model.userlanguage

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "user_languages")
data class UserLanguageItem(
    @PrimaryKey
    val id:Int,
    val user_id:Int?,
    val language:String?,
    val level: String?,
    var created_at:String?,
    var updated_at:String?,
)
