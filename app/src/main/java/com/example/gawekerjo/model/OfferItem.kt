package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "offers")
data class OfferItem(
    @PrimaryKey
    val id:Int,
    var user_id:Int,
    val company_id:Int,
    var title:String,
    var body:String,
    var created_at:String,
    var updated_at:String?,
)
