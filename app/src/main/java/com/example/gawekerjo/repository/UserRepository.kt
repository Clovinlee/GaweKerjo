package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.env
import com.example.gawekerjo.model.UserItem
import com.example.gawekerjo.api.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository {
    val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl(env.API_URL)
        .build()
        .create(UserApi::class.java)

    fun getUser(id : Int?, username : String?){
        val retrofitData = retrofitBuilder.getUser(id, username)

        retrofitData.enqueue(object : Callback<List<UserItem>?>{
            override fun onResponse(call: Call<List<UserItem>?>, response: Response<List<UserItem>?>) {
                val responseBody = response.body()!!
                var listUserItem : ArrayList<UserItem>  = arrayListOf()
                for (r in responseBody){
                    var u = UserItem(r.id, r.name, r.email,
                        r.description, r.gender, r.password,
                        r.birthdate, r.notelp, r.created_at,
                        r.updated_at)
                    listUserItem.add(r)
                }
                
            }

            override fun onFailure(call: Call<List<UserItem>?>, t: Throwable) {
                Log.d("CCD", "Error getting user")
            }

        })
    }
}