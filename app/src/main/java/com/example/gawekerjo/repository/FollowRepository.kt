package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.FollowApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.api.UserApi
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.follow.Follow
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.view.FriendListActivity
import com.example.gawekerjo.view.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class FollowRepository(var db : AppDatabase) {

    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit()

    fun getFriends(mc : FriendListActivity, id: Int, user_id:Int,follow_id:Int){
        var rc_follow : Call<Follow> = rc.create(FollowApi::class.java).getFollows(id, user_id, follow_id)

        rc_follow.enqueue(object: Callback<Follow> {
            override fun onResponse(
                call: Call<Follow>,
                response: Response<Follow>
            ){
                coroutine.launch {
                    // ESTER KERJA DISINI
                }
            }

            override fun onFailure(call: Call<Follow>, t: Throwable) {
                Log.d("CCD", "Error getting FOLLOW")
                Log.d("CCD", t.message.toString())
            }

        })
    }

}