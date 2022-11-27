package com.example.gawekerjo.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gawekerjo.model.PostLikeItem
import com.example.gawekerjo.model.UserItem

interface RememberDao {
    @Insert
    suspend fun insertRemember(usr: UserItem)
    @Delete
    suspend fun deleteRemember(usr: UserItem)

    @Query("SELECT * FROM remember_me")
    suspend fun getRemember():UserItem?
}