package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.language.LanguageItem

@Dao
interface LanguageDao {
    @Insert
    suspend fun insertLanguage(language: LanguageItem)
    @Delete
    suspend fun deleteLanguage(language: LanguageItem)
    @Update
    suspend fun updateLanguage(language: LanguageItem)

    @Query("SELECT * FROM languages")
    suspend fun fetchLanguage():List<LanguageItem>

    @Query("DELETE FROM languages")
    suspend fun clear()
}