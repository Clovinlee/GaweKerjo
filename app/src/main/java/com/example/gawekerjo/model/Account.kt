package com.example.gawekerjo.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
open class Account(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    var description: String?,
    var password: String,
    val notelp: String?,
    val created_at: String,
    var updated_at: String?,
) : Parcelable {
}