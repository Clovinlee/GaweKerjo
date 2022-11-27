package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "companies")
data class CompanyItem(
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    var name:String,
    var description:String,
    var created_at:String,
    var updated_at:String?,
)
