package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.LanguageItem

@Dao
interface LanguageDao {
    @Insert
    suspend fun insertLanguage(language: LanguageItem)
    @Delete
    suspend fun deleteLanguage(language: LanguageItem)
    @Update
    suspend fun updateLanguage(language: LanguageItem)
    @Query("SELECT * FROM languages")
    suspend fun getAllLanguage():List<LanguageItem>
}