package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "skills")
data class SkillItem(
    @PrimaryKey
    val id:Int,
    val name:String,
    var created_at:String,
    var updated_at:String?,
)
