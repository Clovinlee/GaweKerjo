package com.example.gawekerjo.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.post.PostItem
import com.example.gawekerjo.model.postlike.PostLikeItem
import com.example.gawekerjo.model.postlike.postLike
import com.example.gawekerjo.repository.PostLikeRepository
import com.example.gawekerjo.view.HomeActivity
import com.example.gawekerjo.view.HomeFragment
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent
import org.w3c.dom.Text
import retrofit2.Retrofit

class PostAdapter(
    var mc : HomeFragment,
    var data: ArrayList<PostItem>,
    var dataLike: ArrayList<PostLikeItem>,
    var db : AppDatabase,
    var id: Int,

) : RecyclerView.Adapter<PostAdapter.MyHolder>(){

    val coroutine = CoroutineScope(Dispatchers.IO)
    lateinit var postlike : PostLikeRepository

    class MyHolder(it: View) : RecyclerView.ViewHolder(it){
        var title: TextView = it.findViewById(R.id.txtTitle)
        var content: TextView = it.findViewById(R.id.txtContent)
        var ctrlike: TextView = it.findViewById(R.id.txtLike)
        var ctrcomment: TextView = it.findViewById(R.id.txtComment)
        var imglike : ImageView = it.findViewById(R.id.imgLike)
        var imgcomment: ImageView = it.findViewById(R.id.imgComment)
        var imgUser : ImageView = it.findViewById(R.id.imgProfileFriendList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        postlike = PostLikeRepository(db)
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_post, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var itemView = data[position]

        holder.title.text = itemView.title
        holder.content.text = itemView.body
        holder.ctrlike.text = itemView.like_count.toString()

        var flag = 0
        for(i in 0..dataLike.size - 1) {
            if(dataLike[i].post_id == itemView.id)
            { flag = 1 }
        }
//        println("cek flag = " + itemView.id.toString() + " --- " + flag.toString())

        if(flag == 0) {
            holder.imglike.setImageResource(R.drawable.ic_outline_thumb_up_24)
        }
        else {
            holder.imglike.setImageResource(R.drawable.ic_baseline_thumb_up_24)
        }

        holder.imglike.setOnClickListener(View.OnClickListener {
            var flagdalam = 0
            for(i in 0..dataLike.size - 1) {
                if(dataLike[i].post_id == itemView.id)
                { flagdalam = 1 }
            }

            if(flagdalam == 0) {
                postlike.addPostLike(mc, id, itemView.id.toString().toInt())
                holder.imglike.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                dataLike.add(PostLikeItem(10, id, itemView.id, "", ""))
                holder.ctrlike.setText((holder.ctrlike.text.toString().toInt() + 1).toString())
            }
            else {
                postlike.removeLike(mc, id, itemView.id.toString().toInt())
                holder.imglike.setImageResource(R.drawable.ic_outline_thumb_up_24)
                var hapus = -1
                for (i in 0..dataLike.size -1){
                    if(dataLike[i].post_id == itemView.id){
                        hapus = i
                    }
                }
                dataLike.removeAt(hapus)
                holder.ctrlike.setText((holder.ctrlike.text.toString().toInt() - 1).toString())
            }
        })

        holder.imgcomment.setOnClickListener {
            mc.viewDetail(itemView.id, id)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}