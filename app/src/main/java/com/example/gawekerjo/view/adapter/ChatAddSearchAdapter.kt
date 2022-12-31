package com.example.gawekerjo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.model.user.UserItem

class ChatAddSearchAdapter(
    val new_friend:ArrayList<UserItem>,
    val add:(f:UserItem)->Unit
):RecyclerView.Adapter<ChatAddSearchAdapter.VH>() {
    inner class VH(val v: View):RecyclerView.ViewHolder(v) {
        val tv=v.findViewById<TextView>(R.id.tvnamanewfriend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.chat_search_layout,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val nf=new_friend[position]
        holder.tv.text = nf.name
        holder.v.setOnClickListener { add.invoke(nf) }
    }

    override fun getItemCount(): Int {
        return new_friend.size
    }
}