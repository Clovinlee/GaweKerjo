package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.AchievementItem
import com.example.gawekerjo.model.follow.FollowItem

@Dao
interface FollowDao {
    @Insert
    suspend fun insertFollow(f: FollowItem)
    @Delete
    suspend fun deleteFollow(f: FollowItem)
    @Update
    suspend fun updateFollow(f: FollowItem)

    @Query("SELECT * FROM follows")
    suspend fun fetchFollow():List<FollowItem>

    @Query("DELETE FROM follows")
    suspend fun clearFollow()
}