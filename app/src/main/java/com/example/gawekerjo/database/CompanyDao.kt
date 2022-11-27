package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.CompanyItem

@Dao
interface CompanyDao {
    @Insert
    suspend fun insertCompany(company: CompanyItem)
    @Delete
    suspend fun deleteCompany(company: CompanyItem)
    @Update
    suspend fun updateCompany(company: CompanyItem)
    @Query("SELECT * FROM companies")
    suspend fun getAllCompany():List<CompanyItem>
}