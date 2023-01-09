package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.PostLikeApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.postlike.PostLikeItem
import com.example.gawekerjo.model.postlike.postLike
import com.example.gawekerjo.view.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PostLikeRepository(var db: AppDatabase) {
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit()

    fun getPostLikes(mc: HomeFragment, user_id: Int){
        var rc_like : Call<postLike> = rc.create(PostLikeApi::class.java).getPostLikes(user_id)

        rc_like.enqueue(object : Callback<postLike>{
            override fun onResponse(call: Call<postLike>, response: Response<postLike>) {
                coroutine.launch {
                    val responseBody = response.body()
                    var psl : PostLikeItem? = null
                    if(responseBody != null){
                        if(responseBody.status == 200 && responseBody.data.size > 0){
                            db.postlikeDao.clear()
                            for (i in 0 until responseBody.data.size){
                                psl = responseBody.data[i]
                                db.postlikeDao.insertPostLike(psl)
                            }
                        }
                        mc.loadDataLike(true)
                    }
                }
            }

            override fun onFailure(call: Call<postLike>, t: Throwable) {
                Log.d("CCD","Error get like")
                Log.d("CCD",t.message.toString())
            }

        })
    }

    fun addPostLike(mc: HomeFragment, user_id: Int, post_id: Int){
        var rc_like : Call<postLike> = rc.create(PostLikeApi::class.java).addPostLike(user_id, post_id)

        rc_like.enqueue(object : Callback<postLike>{
            override fun onResponse(call: Call<postLike>, response: Response<postLike>) {
                val responseBody = response.body()
                if(responseBody != null){
                    if(responseBody.status == 200){
                        val postLikeBaru = responseBody.data[0]

                        coroutine.launch {
                            db.postlikeDao.insertPostLike(postLikeBaru)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<postLike>, t: Throwable) {
                Log.d("CCD","Error tambah post like")
                Log.d("CCD",t.message.toString())
            }

        })
    }

    fun removeLike(mc: HomeFragment, user_id : Int, post_id : Int){
        var rc_like : Call<postLike> = rc.create(PostLikeApi::class.java).removeLike(user_id, post_id)

        rc_like.enqueue(object : Callback<postLike>{
            override fun onResponse(call: Call<postLike>, response: Response<postLike>) {
                val responseBody = response.body()
                if(responseBody!=null){
                    if(responseBody.status == 200){
                        val psl = responseBody.data[0]
                        coroutine.launch {
                            db.postlikeDao.deletePostLike(psl)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<postLike>, t: Throwable) {
                Log.d("CCD","Error hapus post like")
                Log.d("CCD",t.message.toString())
            }

        })
    }
}