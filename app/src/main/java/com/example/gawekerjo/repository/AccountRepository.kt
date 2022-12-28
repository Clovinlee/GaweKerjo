package com.example.gawekerjo.repository

import android.app.Activity
import android.util.Log
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.api.UserApi
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.view.LoginActivity
import com.example.gawekerjo.view.RegisterActivity
import com.example.gawekerjo.view.RegisterCompanyActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AccountRepository (var db : AppDatabase) {

    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit()

    fun login(mc : LoginActivity, email: String, password: String){
        var rc_user : Call<User> = rc.create(UserApi::class.java).getUser(null, email, password)

        rc_user.enqueue(object: Callback<User>{
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ){
                coroutine.launch {
                    db.userDao.clear()
                    val responseBody = response.body()
                    var usr : UserItem? = null

                    if(responseBody != null){
                        if(responseBody.status == 200 && responseBody.data.size > 0){
                            usr = responseBody.data[0]
                            db.userDao.insertUser(usr)
                        }
                        mc.verifyLogin(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CCD", "Error getting user : $email - $password")
                Log.d("CCD", t.message.toString())
            }

        })
    }

    fun register(mc: Activity, type: Int, email:String,pass:String,name:String, notelp : String = ""){
        var rc_user : Call<User> = rc.create(UserApi::class.java).Register(type, email,pass,name,notelp)

        rc_user.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val rbody = response.body()

                if(rbody != null){

                    if(rbody.status == 200){

                        val usr = rbody.data[0]

                        coroutine.launch {
                            db.userDao.clear()
                            db.userDao.insertUser(usr)
                        }
                    }
                    if(type == 1){
                        Log.d("CCD","TYPE 1")
                        var mc_register = mc as RegisterActivity
                        mc_register.registerCallback(rbody)
                    }else{
                        var mc_register = mc as RegisterCompanyActivity
                        mc_register.registerCallback(rbody)
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CCD","Error Register Account Repository")
                Log.d("CCD",t.message.toString())
            }

        })
    }
}