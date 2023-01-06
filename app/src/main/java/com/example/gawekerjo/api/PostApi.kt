package com.example.gawekerjo.api

import com.example.gawekerjo.model.post.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostApi {
    @GET("posts")
    fun getPosts(
        @Query("id")id: Int?,
        @Query("title") title: String?
    ): Call<Post>
    @POST("addpost")
    fun addPost(
        @Query("user_id")user_id: Int?,
        @Query("title")title: String?,
        @Query("body")body: String?,
    ): Call<Post>

}