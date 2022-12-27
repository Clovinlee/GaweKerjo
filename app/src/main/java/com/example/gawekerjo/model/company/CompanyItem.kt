package com.example.gawekerjo.model.company

import androidx.room.Entity
import com.example.gawekerjo.model.Account

@Entity(tableName = "companies")
class CompanyItem(
    id: Int,
    name: String,
    email: String,
    description: String?,
    notelp: String,
    var lokasi:String?,
    var founded:String?,
    var industry:String?,
    password: String,
    created_at: String,
    updated_at: String?,
) : Account(id, name, email, description, password, notelp,created_at, updated_at){}