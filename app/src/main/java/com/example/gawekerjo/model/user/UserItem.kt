package com.example.gawekerjo.model.user

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize
class UserItem(
    var id: Int,
    var name: String,
    var email: String,
    var description: String?,
    var password: String,
    var notelp: String?,

    // USER
    var gender: String?,
    var birthdate: String?,

    // COMPANY
    var lokasi:String?,
    var founded:String?,
    var industry:String?,

    var created_at: String,
    var updated_at: String?,
) : Parcelable