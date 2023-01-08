package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.PostApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.post.Post
import com.example.gawekerjo.model.post.PostItem
import com.example.gawekerjo.view.HomeActivity
import com.example.gawekerjo.view.NewPostActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*

class PostRepository (var db : AppDatabase) {
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit()

    fun getAllPost(mc: HomeActivity){
        var rc_post : Call<Post> = rc.create(PostApi::class.java).getPosts(null, null)

        rc_post.enqueue(object :Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                coroutine.launch {
                    val responseBody = response.body()
                    var pst : PostItem? = null
                    if(responseBody != null){
                        if(responseBody.status == 200 && responseBody.data.size > 0){
                            db.postDao.clear()
                            for (i in 0 until responseBody.data.size){
                                pst = responseBody.data[i]
                                db.postDao.insertPost(pst)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("CCD", "Error getting post")
                Log.d("CCD", t.message.toString())
            }

        })
    }

    fun tambahPost(mc: NewPostActivity, user_id : Int, title : String, body: String){
        var rc_post :Call<Post> = rc.create(PostApi::class.java).addPost(user_id, title, body)

        rc_post.enqueue(object : Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val responseBody = response.body()
                if(responseBody != null){
                    if(responseBody.status == 200){
                        val postBaru = responseBody.data[0]

                        coroutine.launch {
                            db.postDao.insertPost(postBaru)
                        }
                    }
                    mc.addPostCallback(responseBody)
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("CCD","Error tambah post")
                Log.d("CCD",t.message.toString())
            }

        })
    }
}