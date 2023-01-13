package com.example.gawekerjo.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.api.UserApi
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.comment.CommentItem
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.CommentRepository
import com.example.gawekerjo.view.DetailpostActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CommentAdapter(
    var mc: DetailpostActivity,
    var data : ArrayList<CommentItem>,
    var db : AppDatabase,
    var nama_user : String,
) : RecyclerView.Adapter<CommentAdapter.MyHolder>(){
    val coroutine = CoroutineScope(Dispatchers.IO)
    var arrUser = ArrayList<UserItem>()
    lateinit var commentRepo : CommentRepository


    class MyHolder(it: View) : RecyclerView.ViewHolder(it){
        var title : TextView = it.findViewById(R.id.txtUser)
        var body : TextView = it.findViewById(R.id.txtContent)
        var deskripsiusercomment : TextView = it.findViewById(R.id.txtDeskripsiUserComment)
        var imgUser : ImageView = it.findViewById(R.id.imgProfileFriendList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        commentRepo = CommentRepository(db)
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_comment, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var itemView = data[position]
        holder.body.text = itemView.body


        var rc : Retrofit = RetrofitClient.getRetrofit()
        var rc_user : Call<User> = rc.create(UserApi::class.java).getUser(itemView.user_id, null, null)

        rc_user.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                var rbody = response.body()!!
                if(rbody.status == 200){
                    holder.title.text = rbody.data[0].name
                    holder.deskripsiusercomment.text = rbody.data[0].description
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CCD", "Error getting user getUser UserRepo")
                Log.d("CCD", t.message.toString())
                Log.d("CCD", "===============================")
            }

        })
    }

    override fun getItemCount(): Int {
        return data.size
    }
}