package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.user.UserItem

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserItem)
    @Delete
    suspend fun deleteUser(user: UserItem)
    @Update
    suspend fun updateUser(user: UserItem)

    @Query("DELETE FROM users")
    suspend fun clear()

    @Query("SELECT * FROM users")
    suspend fun getAllUser():List<UserItem>
    @Query("SELECT * FROM users WHERE email=:email")
    suspend fun getUserByEmail(email:String): UserItem?
    @Query("SELECT * FROM users WHERE email=:email AND password=:password")
    suspend fun getUserByEmailPassword(email:String,password:String): UserItem?

    @Query("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    suspend fun getLastUser(): UserItem?

    @Query("SELECT * FROM users WHERE id=:user_id")
    suspend fun getUserById(user_id:Int): UserItem?
}