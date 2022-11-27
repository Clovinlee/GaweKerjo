package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.SkillItem

@Dao
interface SkillDao {
    @Insert
    suspend fun insertSkill(skill: SkillItem)
    @Delete
    suspend fun deleteSkill(skill: SkillItem)
    @Update
    suspend fun updateSkill(skill: SkillItem)
    @Query("SELECT * FROM skills")
    suspend fun getAllSkill():List<SkillItem>
}