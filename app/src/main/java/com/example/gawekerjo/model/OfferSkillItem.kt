package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "offer_skills")
data class OfferSkillItem(
    @PrimaryKey
    val id:Int,
    val offer_id:Int,
    var skill_id:Int,
    var created_at:String,
    var updated_at:String?,
)
