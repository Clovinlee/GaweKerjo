package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.userlanguage.UserLanguageItem

@Dao
interface UserLanguageDao {
    @Insert
    suspend fun insertUserLanguage(userlanguage: UserLanguageItem)
    @Delete
    suspend fun deleteUserLanguage(userlanguage: UserLanguageItem)
    @Update
    suspend fun updateUserLanguage(userlanguage: UserLanguageItem)
    @Query("SELECT * FROM user_languages")
    suspend fun getAllUserLanguage():List<UserLanguageItem>
    @Query("DELETE FROM user_languages")
    suspend fun clear()
}