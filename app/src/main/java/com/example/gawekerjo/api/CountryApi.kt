package com.example.gawekerjo.api

import com.example.gawekerjo.model.country.Country
import retrofit2.Call
import retrofit2.http.GET

interface CountryApi {
    @GET("all")
    fun getAllCountries(
    ): Call<List<Country>>
}