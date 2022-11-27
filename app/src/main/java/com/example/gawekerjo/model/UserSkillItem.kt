package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "user_skills")
data class UserSkillItem(
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    val skill_id:Int,
    var created_at:String,
    var updated_at:String?,
)
