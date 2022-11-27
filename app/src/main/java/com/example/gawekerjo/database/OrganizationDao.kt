package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.OrganizationItem

@Dao
interface OrganizationDao {
    @Insert
    suspend fun insertOrganization(organization: OrganizationItem)
    @Delete
    suspend fun deleteOrganization(organization: OrganizationItem)
    @Update
    suspend fun updateOrganization(organization: OrganizationItem)
    @Query("SELECT * FROM organizations")
    suspend fun getAllOrganization():List<OrganizationItem>
}