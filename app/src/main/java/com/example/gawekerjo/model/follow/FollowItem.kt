package com.example.gawekerjo.model.follow

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "follows")
@Parcelize
class FollowItem (
    @PrimaryKey
    val id:Int,
    val user_id:Int,
    val follow_id:Int,
    var created_at:String,
    var updated_at:String?,
): Parcelable