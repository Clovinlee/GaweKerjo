package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.CommentApi
import com.example.gawekerjo.api.PostCommentApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.comment.Comment
import com.example.gawekerjo.model.comment.CommentItem
import com.example.gawekerjo.model.postcomment.PostComment
import com.example.gawekerjo.view.DetailpostActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CommentRepository(var db : AppDatabase) {
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit()

    fun addPostComment(mc : DetailpostActivity, user_id : Int, post_id : Int, body : String){
        var rc_comment : Call<Comment> = rc.create(CommentApi::class.java).addPostComment(user_id, post_id, body)

        rc_comment.enqueue(object : Callback<Comment>{
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                val responseBody = response.body()
                if(responseBody != null){
                    if(responseBody.status == 200){
                        val cmnt = responseBody.data[0]
                        coroutine.launch {
                            db.commentDao.insertComment(cmnt)
                        }
                    }
                    mc.addCommentCallback(responseBody)
                }
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                Log.d("CCD","Error add comment")
                Log.d("CCD",t.message.toString())
            }

        })
    }

    fun getAllComment(mc : DetailpostActivity, post_id : Int){
        var rc_comment : Call<Comment> = rc.create(CommentApi::class.java).getAllComment(post_id)

        rc_comment.enqueue(object : Callback<Comment>{
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                coroutine.launch {
                    val responseBody = response.body()
                    var cmnt : CommentItem? = null
                    if(responseBody != null){
                        if(responseBody.status == 200 && responseBody.data.size > 0){
                            db.commentDao.clear()
                            for (i in 0 until responseBody.data.size){
                                cmnt = responseBody.data[i]
                                db.commentDao.insertComment(cmnt)
                            }
                        }
                        mc.loadComment(true)
                    }
                }
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                Log.d("CCD","Error get comment")
                Log.d("CCD",t.message.toString())
            }

        })
    }
}