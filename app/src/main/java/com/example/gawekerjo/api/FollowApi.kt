package com.example.gawekerjo.api

import com.example.gawekerjo.model.follow.Follow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FollowApi {
    @GET("follows")
    fun getFollows(
        @Query("id")id:Int,
        @Query("follow_id")follow_id:Int,
        @Query("user_id")user_id:Int,
    ): Call<Follow>
    @POST("addfollows")
    fun newFollow(
        @Query("follow_id")follow_id:Int,
        @Query("user_id")user_id:Int,
    ): Call<Follow>
}