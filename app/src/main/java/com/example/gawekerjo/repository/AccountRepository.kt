package com.example.gawekerjo.repository

import android.app.Activity
import android.util.Log
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.api.SkillApi
import com.example.gawekerjo.api.UserApi
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.chat.ChatItem
import com.example.gawekerjo.model.skill.Skill
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.model.userchat.UserChatItem
import com.example.gawekerjo.view.*
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

    fun getFriend(
        id: Int,
        ca: ChatActivity,
        listchat: ArrayList<ChatItem>,
        listdchat: ArrayList<UserChatItem>
    ){
        var rc_friend=rc.create(UserApi::class.java).getFriend(id)
        rc_friend.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                coroutine.launch {
                    //db.chatDao.clear(id)
                    val responseBody = response.body()
                    val listfriend= arrayListOf<UserItem>()
                    if(responseBody != null){
                        if(responseBody.status == 200 && responseBody.data.isNotEmpty()){
                            listfriend.addAll(responseBody.data)
                        }
                        getNewFriend(id,ca,listchat,listdchat,listfriend)
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CCD", "Error getting chat : $id")
                Log.d("CCD", t.message.toString())
            }
        })
    }
    fun getNewFriend(
        id: Int,
        ca: ChatActivity,
        listchat: ArrayList<ChatItem>,
        listdchat: ArrayList<UserChatItem>,
        listfriend: ArrayList<UserItem>
    ){
        var rc_friend=rc.create(UserApi::class.java).getNewFriend(id)
        rc_friend.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                coroutine.launch {
                    //db.chatDao.clear(id)
                    val responseBody = response.body()
                    val newfriends= arrayListOf<UserItem>()
                    if(responseBody != null){
                        if(responseBody.status == 200 && responseBody.data.isNotEmpty()){
                            newfriends.addAll(responseBody.data)
                        }
                        ca.Start(listchat,listdchat,listfriend,newfriends)
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CCD", "Error getting chat : $id")
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


    fun editprofile(
        mc: EditProfileUserActivity,
        id: Int,
        name: String,
        description: String,
        notelp: String

    ){
        var rc_user : Call<User> = rc.create(UserApi::class.java).editProfile( id, name, description, notelp )

        rc_user.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val rbody = response.body()

                if(rbody != null){

                    if(rbody.status == 200){

                        val usr = rbody.data[0]

                        coroutine.launch {

                            db.userDao.updateUser(usr)
                        }

                        var mc_edit = mc as EditProfileUserActivity
                        mc_edit.balek(rbody)


                    }

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CCD","Error Edit Profile")
                Log.d("CCD",t.message.toString())
            }

        })
    }


}