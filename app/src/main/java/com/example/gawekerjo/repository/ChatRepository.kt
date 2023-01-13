package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.ChatApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.api.UserChatApi
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.chat.Chat
import com.example.gawekerjo.model.chat.ChatItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.model.userchat.UserChat
import com.example.gawekerjo.model.userchat.UserChatItem
import com.example.gawekerjo.view.ChatActivity
import com.example.gawekerjo.view.DetailChatActivity
import com.example.gawekerjo.view.FriendListActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRepository(var db:AppDatabase) {
    private val c=CoroutineScope(Dispatchers.IO)
    var rc=RetrofitClient.getRetrofit()

    fun getDChat(id:Int,ca:ChatActivity,listchat:ArrayList<ChatItem>){
        var rc_dchat=rc.create(UserChatApi::class.java).getDChat(id)
        rc_dchat.enqueue(object :Callback<UserChat>{
            override fun onResponse(call: Call<UserChat>, response: Response<UserChat>) {
                c.launch {
                    val responseBody = response.body()
                    val listdchat= arrayListOf<UserChatItem>()
                    if(responseBody != null){
                        if(responseBody.status == 200 && responseBody.data.isNotEmpty()){
                            listdchat.addAll(responseBody.data)
                            val ar=AccountRepository(db)
                            ar.getFriend(id,ca,listchat,listdchat)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<UserChat>, t: Throwable) {
                Log.d("CCD", "Error getting user chat : $id")
                Log.d("CCD", t.message.toString())
            }

        })
    }
    fun getChat(id:Int, ca:ChatActivity){
        var rc_chat=rc.create(ChatApi::class.java).getChat(id)
        rc_chat.enqueue(object : Callback<Chat>{
            override fun onResponse(call: Call<Chat>, response: Response<Chat>) {
                c.launch {
                    val responseBody = response.body()
                    val listchat= arrayListOf<ChatItem>()
                    if(responseBody != null){
                        if(responseBody.status == 200 && responseBody.data.isNotEmpty()){
                            listchat.addAll(responseBody.data)
                        }
                        this@ChatRepository.getDChat(id,ca,listchat)
                        //ca.Start(listchat)
                    }
                }
            }

            override fun onFailure(call: Call<Chat>, t: Throwable) {
                Log.d("CCD", "Error getting chat : $id")
                Log.d("CCD", t.message.toString())
            }
        })
    }
    fun newChat(sender:Int,Recipient:Int,ca: ChatActivity){
        var rc_chat=rc.create(ChatApi::class.java).newChat(sender,Recipient)
        rc_chat.enqueue(object :Callback<Chat>{
            override fun onResponse(call: Call<Chat>, response: Response<Chat>) {
                c.launch {
                    val responseBody = response.body()
                    if(responseBody != null){
                        if(responseBody.status == 200 && responseBody.data.isNotEmpty()){
                            ca.newChat(responseBody.data[0])
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Chat>, t: Throwable) {
                Log.d("CCD", "Error getting chat ")
                Log.d("CCD", t.message.toString())
            }

        })
    }
    fun addChat(da:DetailChatActivity,user_id:Int,chat_id:Int,message:String){
        var rc_dchat=rc.create(UserChatApi::class.java).addChat(user_id, chat_id, message)
        rc_dchat.enqueue(object :Callback<UserChat>{
            override fun onResponse(call: Call<UserChat>, response: Response<UserChat>) {
                c.launch {
                    val responseBody = response.body()
                    if(responseBody != null){
                        if(responseBody.status == 200 && responseBody.data.isNotEmpty()){
                            //val ar=AccountRepository(db)
                            da.Chat(responseBody.data[0])
                        }

                    }
                }
            }

            override fun onFailure(call: Call<UserChat>, t: Throwable) {
                Log.d("CCD", "Error getting user chat")
                Log.d("CCD", t.message.toString())
            }

        })
    }
    fun friendtoChat(fa:FriendListActivity, user_id:Int, recipient: UserItem){
        var rc_chat=rc.create(ChatApi::class.java).friendtoChat(user_id, recipient.id)
        rc_chat.enqueue(object :Callback<Chat>{
            override fun onResponse(call: Call<Chat>, response: Response<Chat>) {
                c.launch {
                    val responseBody = response.body()
                    if(responseBody != null){
                        if(responseBody.status == 200 && responseBody.data.isNotEmpty()){
                            //val ar=AccountRepository(db)
                            this@ChatRepository.friendtoDchat(fa,responseBody.data[0],recipient)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<Chat>, t: Throwable) {
                Log.d("CCD", "Error getting chat")
                Log.d("CCD", t.message.toString())
            }

        })
    }

    private fun friendtoDchat(fa: FriendListActivity, hchat: ChatItem, recipient: UserItem) {
        var rc_dchat=rc.create(UserChatApi::class.java).friendtoDchat(hchat.id)
        rc_dchat.enqueue(object :Callback<UserChat>{
            override fun onResponse(call: Call<UserChat>, response: Response<UserChat>) {
                c.launch {
                    val responseBody = response.body()
                    if(responseBody != null){
                        if(responseBody.status == 200){
                            fa.gotoChat(hchat,responseBody.data,recipient)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<UserChat>, t: Throwable) {
                Log.d("CCD", "Error getting user chat ")
                Log.d("CCD", t.message.toString())
            }

        })
    }
}