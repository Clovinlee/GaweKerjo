package com.example.gawekerjo.model.country

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
class CountryItem (
    @PrimaryKey
    var name : String
)