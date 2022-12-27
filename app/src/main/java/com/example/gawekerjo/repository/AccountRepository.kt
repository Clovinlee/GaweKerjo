package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.CompanyApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.api.UserApi
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.company.Company
import com.example.gawekerjo.model.company.CompanyItem
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

    fun loginCompany(mc : LoginActivity, email: String, password: String){
        var rc_company : Call<Company> = rc.create(CompanyApi::class.java).getCompany(null, email, password)

        rc_company.enqueue(object: Callback<Company>{
            override fun onResponse(
                call: Call<Company>,
                response: Response<Company>
            ){
                coroutine.launch {
                    db.companyDao.clear()
                    val responseBody = response.body()
                    var cmp : CompanyItem? = null

                    if(responseBody != null){
                        if(responseBody.status == 200 && responseBody.data.size > 0){
                            cmp = responseBody.data[0]
                            db.companyDao.insertCompany(cmp)
                        }
                        mc.verifyLoginCompany(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<Company>, t: Throwable) {
                Log.d("CCD", "Error getting user : $email - $password")
                Log.d("CCD", t.message.toString())
            }

        })
    }

    fun loginUser(mc : LoginActivity, email: String, password: String){
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
                        mc.verifyLoginUser(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CCD", "Error getting user : $email - $password")
                Log.d("CCD", t.message.toString())
            }

        })
    }

    fun registerUser(mc: RegisterActivity, email:String,pass:String,name:String){
        var rc_user : Call<User> = rc.create(UserApi::class.java).Register(email,pass,name)

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
                    mc.registerCallback(rbody)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CCD","Error Register")
                Log.d("CCD",t.message.toString())
            }

        })
    }

    fun registerCompany(mc: RegisterCompanyActivity, name : String, email : String, notelp : String, password : String){
        var rc_company : Call<Company> = rc.create(CompanyApi::class.java).register(name, email, notelp, password)

        rc_company.enqueue(object : Callback<Company> {
            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                val responseBody = response.body()

                if(responseBody != null){
                    if(responseBody.status == 200){
                        coroutine.launch {
                            db.companyDao.clear()

                            val cmp : CompanyItem = responseBody.data[0]

                            db.companyDao.insertCompany(cmp)
                        }
                    }
                    mc.registerCallback(responseBody)
                }

            }

            override fun onFailure(call: Call<Company>, t: Throwable) {
                Log.d("CCD","Fail to get Company API")
            }
        })
    }

}