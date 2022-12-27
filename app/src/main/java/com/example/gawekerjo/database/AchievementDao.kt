package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.AchievementItem

@Dao
interface AchievementDao {
    @Insert
    suspend fun insertAchievement(achievement: AchievementItem)
    @Delete
    suspend fun deleteAchievement(achievement: AchievementItem)
    @Update
    suspend fun updateAchievement(achievement: AchievementItem)

    @Query("SELECT * FROM achievements")
    suspend fun fetchAchievement():List<AchievementItem>
}