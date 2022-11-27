package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.UserSkillItem

@Dao
interface UserSkillDao {
    @Insert
    suspend fun insertUserSkill(userskill: UserSkillItem)
    @Delete
    suspend fun deleteUserSkill(userskill: UserSkillItem)
    @Update
    suspend fun updateUserSkill(userskill: UserSkillItem)
    @Query("SELECT * FROM user_skills")
    suspend fun getAllUserSkill():List<UserSkillItem>
}