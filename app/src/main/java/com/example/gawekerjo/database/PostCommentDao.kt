package com.example.gawekerjo.database

import androidx.room.*
import com.example.gawekerjo.model.postcomment.PostCommentItem

@Dao
interface PostCommentDao {
    @Insert
    suspend fun insertPostComment(postcomment: PostCommentItem)
    @Delete
    suspend fun deletePostComment(postcomment: PostCommentItem)
    @Update
    suspend fun updatePostComment(postcomment: PostCommentItem)
    @Query("SELECT * FROM post_comments")
    suspend fun getAllPostComment():List<PostCommentItem>
}