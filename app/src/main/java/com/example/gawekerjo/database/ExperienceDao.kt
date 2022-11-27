package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.ExperienceItem

@Dao
interface ExperienceDao {
    @Insert
    suspend fun insertExperience(experience: ExperienceItem)
    @Delete
    suspend fun deleteExperience(experience: ExperienceItem)
    @Update
    suspend fun updateExperience(experience: ExperienceItem)
    @Query("SELECT * FROM experiences")
    suspend fun getAllExperience():List<ExperienceItem>
}