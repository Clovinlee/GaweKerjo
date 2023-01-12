package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.comment.CommentItem

@Dao
interface CommentDao {
    @Insert
    suspend fun insertComment(comment: CommentItem)
    @Delete
    suspend fun deleteComment(comment: CommentItem)
    @Update
    suspend fun updateComment(comment: CommentItem)
    @Query("SELECT * FROM comments")
    suspend fun getAllComment():List<CommentItem>
    @Query("DELETE FROM comments")
    suspend fun clear()
}