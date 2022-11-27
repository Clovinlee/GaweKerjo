package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "organizations")
data class OrganizationItem(
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    var name:String,
    var description:String,
    var date_start:String,
    var date_end:String,
    var created_at:String,
    var updated_at:String?,
)
