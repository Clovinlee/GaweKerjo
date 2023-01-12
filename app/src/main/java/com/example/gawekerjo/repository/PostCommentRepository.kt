package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.PostCommentApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.postcomment.PostComment
import com.example.gawekerjo.view.DetailpostActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*

class PostCommentRepository(var db : AppDatabase) {
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit()

    fun addPostComment(mc : DetailpostActivity, user_id : Int, post_id : Int){
        var rc_comment : Call<PostComment> = rc.create(PostCommentApi::class.java).addPostComment(user_id, post_id)

        rc_comment.enqueue(object : Callback<PostComment>{
            override fun onResponse(call: Call<PostComment>, response: Response<PostComment>) {
                val responseBody = response.body()
                if(responseBody != null){
                    if(responseBody.status == 200){
                        val cmnt = responseBody.data[0]

                        coroutine.launch {
                            db.postcommentDao.insertPostComment(cmnt)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PostComment>, t: Throwable) {
                Log.d("CCD","Error add comment")
                Log.d("CCD",t.message.toString())
            }

        })
    }
}