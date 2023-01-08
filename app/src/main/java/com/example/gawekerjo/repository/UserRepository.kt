package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.api.UserApi
import com.example.gawekerjo.model.user.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserRepository {

    private var rc : Retrofit = RetrofitClient.getRetrofit()

    fun getUser(id : Int?, email : String?, password : String?){

    }
}