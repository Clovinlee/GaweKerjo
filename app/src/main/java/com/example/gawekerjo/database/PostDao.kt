package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.post.PostItem

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
    @Query("DELETE FROM posts")
    suspend fun clear()

    @Query("SELECT * FROM posts WHERE id=:post_id")
    suspend fun getPostById(post_id: Int): PostItem?
}