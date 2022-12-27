package com.example.gawekerjo.model.user

import androidx.room.Entity
import com.example.gawekerjo.model.Account

@Entity(tableName = "users")
class UserItem(
    id: Int,
    name: String,
    email: String,
    description: String?,
    password: String,
    notelp: String?,

    // USER
    var gender: String?,
    var birthdate: String?,

    // COMPANY
    var lokasi:String?,
    var founded:String?,
    var industry:String?,

    created_at: String,
    updated_at: String?,
) : Account(id, name, email, description, password, notelp, created_at, updated_at){}