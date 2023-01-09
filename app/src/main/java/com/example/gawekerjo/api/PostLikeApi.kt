package com.example.gawekerjo.api


import com.example.gawekerjo.model.postlike.postLike
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostLikeApi {
    @GET("postlikes")
    fun getPostLikes(
        @Query("user_id") user_id : Int?,
    ) : Call<postLike>
    @POST("addlike")
    fun addPostLike(
        @Query("user_id") user_id : Int?,
        @Query("post_id") post_id : Int?,
    ) : Call<postLike>
    @POST("deletelike")
    fun removeLike(
        @Query("user_id") user_id : Int?,
        @Query("post_id") post_id : Int?,
    ): Call<postLike>
}