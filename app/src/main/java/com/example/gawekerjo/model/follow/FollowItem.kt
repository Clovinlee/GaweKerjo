package com.example.gawekerjo.model.follow

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "follows")
class FollowItem (
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    val follow_id:Int,
    var created_at:String,
    var updated_at:String?,
)