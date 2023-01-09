package com.example.gawekerjo.api

import com.example.gawekerjo.model.userlanguage.UserLanguage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LanguageApi {

    @GET("getuserlanguages")
    fun getUserLanguages(
        @Query("id")id: Int?,
        @Query("user_id")user_id: Int?
    ):Call<UserLanguage>
    @POST("adduserlanguages")
    fun addUserLanguages(
        @Query("user_id")user_id: Int?,
        @Query("name")name: String?,
        @Query("level")level: String?
    ):Call<UserLanguage>
    @POST("deleteuserlanguages")
    fun deleteUserLanguages(
        @Query("id")id: Int?
    ):Call<UserLanguage>
}