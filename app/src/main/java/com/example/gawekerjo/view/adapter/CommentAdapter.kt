package com.example.gawekerjo.view.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.comment.CommentItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.CommentRepository
import com.example.gawekerjo.repository.UserRepository
import com.example.gawekerjo.view.DetailpostActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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
        var title : TextView = it.findViewById(R.id.txtTitle)
        var body : TextView = it.findViewById(R.id.txtContent)
        var imgUser : ImageView = it.findViewById(R.id.imgProfileFriendList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        commentRepo = CommentRepository(db)
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_comment, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var itemView = data[position]
        holder.title.text = nama_user
        holder.body.text = itemView.body
    }

    override fun getItemCount(): Int {
        return data.size
    }
}