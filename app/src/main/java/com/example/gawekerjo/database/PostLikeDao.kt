package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.PostLikeItem

@Dao
interface PostLikeDao {
    @Insert
    suspend fun insertPostLike(postlike: PostLikeItem)
    @Delete
    suspend fun deletePostLike(postlike: PostLikeItem)
    @Update
    suspend fun updatePostLike(postlike: PostLikeItem)
    @Query("SELECT * FROM post_likes")
    suspend fun getAllPostLike():List<PostLikeItem>
}