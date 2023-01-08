package com.example.gawekerjo.api

import com.example.gawekerjo.model.Offer.Offer
import com.example.gawekerjo.model.education.Education
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OfferApi {
    @GET("offers")
    fun getOffers(
        @Query("id")id: Int?,
        @Query("user_id")user_id: Int?,
    ): Call<Offer>

    @GET("searchoffers")
    fun searchOffer(
        @Query("title")title : String?
    ): Call<Offer>

    @POST("deleteoffer")
    fun deleteOffer(
        @Query("id") id:Int,
    ): Call<Offer>

    @POST("editoffer")
    fun editOffer(
        @Query("id")id:Int?,
        @Query("title")title:String?,
        @Query("body")body:String?,
        @Query("skills")skills:String?,
    ) : Call<Offer>

    @POST("addoffer")
    fun addOffer(
        @Query("user_id")user_id:Int?,
        @Query("title")title:String?,
        @Query("body")body:String?,
        @Query("skills")skills:String?,
    ): Call<Offer>
}