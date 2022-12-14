package com.example.gawekerjo.model.language

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "languages")
data class LanguageItem(
    @PrimaryKey
    val id:Int,
    var name:String,
    var created_at:String,
    var updated_at:String?,
)
