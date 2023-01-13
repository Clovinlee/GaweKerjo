package com.example.gawekerjo.api

import android.content.Context
import android.util.Log
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
        var urlNow : String = env.API_URL

        fun getRetrofit(API_URL : String? = env.API_URL) : Retrofit {
            Log.d("CCD", "REPO + "+API_URL)
            if(instance == null || urlNow != API_URL){
                val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                val client : OkHttpClient = OkHttpClient.Builder().apply {
                    addInterceptor(interceptor).addInterceptor {
                        chain -> val request = chain.request().newBuilder()
                        .addHeader("api_token", env.API_TOKEN)
                        chain.proceed(request.build())
                    }
                }.build()

                instance = Retrofit.Builder().baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(client)
                    .build()

                this.urlNow = API_URL!!
            }

            return instance!!
        }
    }
}