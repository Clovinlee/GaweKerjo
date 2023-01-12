package com.example.gawekerjo.api

import com.example.gawekerjo.model.postcomment.PostComment
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface PostCommentApi {
    @POST("addcomment")
    fun addPostComment(
        @Query("user_id") user_id : Int?,
        @Query("post_id") post_id : Int?,
    ) : Call<PostComment>
}