package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.env
import com.example.gawekerjo.model.UserItem
import com.example.gawekerjo.api.UserApi
import com.example.gawekerjo.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository(var db : AppDatabase) {

    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit()

    suspend fun addUser(){
        //KERJA DISINI KWAN
    }

    suspend fun loadUserData(){
        var rc_user : Call<List<UserItem>> = rc.create(UserApi::class.java).getUser(null, null, null)

        rc_user.enqueue(object: Callback<List<UserItem>?>{
            override fun onResponse(
                call: Call<List<UserItem>?>,
                response: Response<List<UserItem>?>
            ) {
                val responseBody = response.body()!!

                coroutine.launch {
                    db.userDao.clear()

                    for (r in responseBody){
                    var u : UserItem = UserItem(r.id, r.name, r.email, r.description,
                    r.gender, r.password, r.birthdate, r.notelp, r.created_at, r.updated_at)

                        db.userDao.insertUser(u)
                    }
                }
            }

            override fun onFailure(call: Call<List<UserItem>?>, t: Throwable) {
                Log.d("CCD", "Error getting user")
                Log.d("CCD", t.message.toString())
            }

        })
    }
    fun Register(email:String,pass:String,name:String){
        var rc : Retrofit = RetrofitClient.getRetrofit()
        var rc_user : Call<UserItem> = rc.create(UserApi::class.java).Register(email,pass,name)
        rc_user.enqueue(object : Callback<UserItem?>{
            override fun onResponse(call: Call<UserItem?>, response: Response<UserItem?>) {
                val rbody=response.body()!!
                coroutine.launch { db.userDao.insertUser(rbody) }
            }

            override fun onFailure(call: Call<UserItem?>, t: Throwable) {
                Log.d("CCD","Error Register")
                Log.d("CCD",t.message.toString())
            }

        })
    }
}