package com.example.gawekerjo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.model.post.PostItem
import com.example.gawekerjo.view.HomeActivity
import com.example.gawekerjo.view.HomeFragment
import org.w3c.dom.Text
import retrofit2.Retrofit

class PostAdapter(
    var mc : HomeFragment,
    var data: ArrayList<PostItem>,
    var idx : Int,
) : RecyclerView.Adapter<PostAdapter.MyHolder>(){

    class MyHolder(it: View) : RecyclerView.ViewHolder(it){
        var title: TextView = it.findViewById(R.id.txtTitle)
        var content: TextView = it.findViewById(R.id.txtContent)
        var ctrlike: TextView = it.findViewById(R.id.txtLike)
        var ctrcomment: TextView = it.findViewById(R.id.txtComment)
        var imglike : ImageView = it.findViewById(R.id.imgLike)
        var imgcomment: ImageView = it.findViewById(R.id.imgComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_post, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var itemView = data[position]

        holder.title.text = itemView.title
        holder.content.text = itemView.body
        holder.ctrlike.text = itemView.like_count.toString()


    }

    override fun getItemCount(): Int {
        return data.size
    }

}