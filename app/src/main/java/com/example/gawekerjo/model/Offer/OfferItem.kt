package com.example.gawekerjo.model.Offer

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "offers")
@Parcelize
data class OfferItem(
    @PrimaryKey
    val id:Int,
    var user_id:Int,
    var title:String,
    var body:String,
    var skills:String,
    var created_at:String?,
    var updated_at:String?,
) : Parcelable
