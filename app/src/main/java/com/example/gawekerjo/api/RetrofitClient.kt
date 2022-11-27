package com.example.gawekerjo.api

import android.content.Context
import androidx.room.Room
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.env
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


abstract class RetrofitClient {

    companion object {
        @get:Synchronized
        var instance: Retrofit? = null

        fun getRetrofit() : Retrofit {
            if(instance == null){
                instance = Retrofit.Builder().baseUrl(env.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return instance!!
        }
    }
}