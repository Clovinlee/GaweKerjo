package com.example.gawekerjo.api

import android.content.Context
import androidx.room.Room
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.env
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


abstract class RetrofitClient {

    companion object {
        @get:Synchronized
        var instance: Retrofit? = null

        fun getRetrofit(API_URL : String = env.API_URL) : Retrofit {
            if(instance == null){
                val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                val client : OkHttpClient = OkHttpClient.Builder().apply {
                    addInterceptor(interceptor)
                }.build()

                instance = Retrofit.Builder().baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(client)
                    .build()
            }

            return instance!!
        }
    }
}