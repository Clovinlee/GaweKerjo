package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.PostItem

@Dao
interface PostDao {
    @Insert
    suspend fun insertPost(post: PostItem)
    @Delete
    suspend fun deletePost(post: PostItem)
    @Update
    suspend fun updatePost(post: PostItem)
    @Query("SELECT * FROM posts")
    suspend fun getAllPost():List<PostItem>
}