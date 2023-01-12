package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.country.CountryItem

@Dao
interface CountryDao {
    @Insert
    suspend fun insertCountry(country: CountryItem)
    @Delete
    suspend fun deleteCountry(country: CountryItem)
    @Update
    suspend fun updateCountry(country: CountryItem)

    @Query("SELECT * FROM country")
    suspend fun fetchCountry():List<CountryItem>

    @Query("DELETE FROM country")
    suspend fun clear()
}