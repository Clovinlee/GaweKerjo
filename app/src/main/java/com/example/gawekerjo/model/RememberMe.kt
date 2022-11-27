package com.example.gawekerjo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "remember_me")
data class Rememberme(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    var description: String,
    var gender: String,
    var password: String,
    var birthdate: String,
    val notelp: String,
    val created_at: String,
    var updated_at: String?,
)
