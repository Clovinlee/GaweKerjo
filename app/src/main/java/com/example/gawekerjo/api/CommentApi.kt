package com.example.gawekerjo.api

import com.example.gawekerjo.model.comment.Comment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CommentApi {
    @POST("addcomment")
    fun addPostComment(
        @Query("user_id") user_id : Int?,
        @Query("post_id") post_id : Int?,
        @Query("body") body: String?,
    ) : Call<Comment>
    @GET("getComment")
    fun getAllComment(
        @Query("post_id") post_id : Int?,
    ) : Call<Comment>
}