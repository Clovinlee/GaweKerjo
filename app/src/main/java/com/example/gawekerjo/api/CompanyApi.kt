package com.example.gawekerjo.api

import com.example.gawekerjo.model.company.Company
import com.example.gawekerjo.model.company.CompanyItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CompanyApi {
    @GET("companies")
    fun getCompany(
        @Query("id") id : Int?,
        @Query("email") email : String?,
        @Query("password") password : String?,
    ): Call<Company>

    @GET("searchCompany")
    fun searchCompany():Call<CompanyItem>
}