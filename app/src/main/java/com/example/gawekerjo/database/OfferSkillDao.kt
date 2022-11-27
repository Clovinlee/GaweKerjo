package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.OfferSkillItem

@Dao
interface OfferSkillDao {
    @Insert
    suspend fun insertOfferSkill(offerskill: OfferSkillItem)
    @Delete
    suspend fun deleteOfferSkill(offerskill: OfferSkillItem)
    @Update
    suspend fun updateOfferSkill(offerskill: OfferSkillItem)
    @Query("SELECT * FROM offer_skills")
    suspend fun getAllOfferSkill():List<OfferSkillItem>
}