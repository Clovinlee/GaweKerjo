package com.example.gawekerjo.repository

import com.example.gawekerjo.api.PostApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.post.Post
import com.example.gawekerjo.view.NewPostActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.*

class PostRepository (var db : AppDatabase) {
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit()

    fun getAllPost(mc: NewPostActivity){
        var rc_post : Call<Post> = rc.create(PostApi::class.java).getPosts(null, null)

        rc_post.enqueue(object :Callback<Post>{
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}