package com.example.gawekerjo.model.user

import androidx.room.Entity
import com.example.gawekerjo.model.Account

@Entity(tableName = "users")
class UserItem(
    id: Int,
    name: String,
    email: String,
    description: String?,
    var gender: String?,
    password: String,
    var birthdate: String?,
    notelp: String,
    created_at: String,
    updated_at: String?,
) : Account(id, name, email, description, password, notelp, created_at, updated_at){}