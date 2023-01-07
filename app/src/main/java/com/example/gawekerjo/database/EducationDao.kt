package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.education.EducationItem

@Dao
interface EducationDao {
    @Insert
    suspend fun insertEducation(education: EducationItem)
    @Delete
    suspend fun deleteEducation(education: EducationItem)
    @Update
    suspend fun updateEducation(education: EducationItem)
    @Query("SELECT * FROM educations")
    suspend fun getAllEducation():List<EducationItem>
    @Query("DELETE FROM educations")
    suspend fun clear()
}