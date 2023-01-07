package com.example.gawekerjo.api

import com.example.gawekerjo.model.skill.Skill
import com.example.gawekerjo.model.userskill.UserSkill
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SkillApi {

    @GET("skills")
    fun getSkills(
        @Query("id")id: Int?,
        @Query("name") name: String?
    ) :Call<Skill>
    @POST("adduserskill")
    fun addUserSkill(
        @Query("user_id")user_Id:Int?,
        @Query("skill_id")skill_id:Int?
    ):Call<UserSkill>
    @GET("userskill")
    fun getUserSkill(
        @Query("id")id: Int?,
        @Query("user_id")user_id: Int?,
        @Query("skill_id")skill_id:Int?
    ):Call<UserSkill>
    @POST("deleteuserskill")
    fun deleteUserSkill(
        @Query("id") id: Int?
    ):Call<UserSkill>
}