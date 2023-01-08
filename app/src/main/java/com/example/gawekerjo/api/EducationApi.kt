package com.example.gawekerjo.api

import com.example.gawekerjo.model.education.Education
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Date

interface EducationApi {

    @POST("addeducation")
    fun addEducation(
        @Query("user_id")user_id: Int,
        @Query("name")name: String,
        @Query("date_start")date_start: String,
        @Query("date_end")date_end: String,
        @Query("score")score: String
    ): Call<Education>
    @GET("educations")
    fun geteducation(
        @Query("id")id: Int?,
        @Query("user_id")user_id:Int?
    ):Call<Education>
}