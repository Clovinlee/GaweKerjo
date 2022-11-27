package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "educations")
data class EducationItem(
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    var name:String,
    var date_start:String,
    var date_end:String,
    var score:String,
    var created_at:String,
    var updated_at:Any
)
