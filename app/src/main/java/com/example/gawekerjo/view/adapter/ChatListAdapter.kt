package com.example.gawekerjo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.model.chat.ChatItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.model.userchat.UserChatItem

class ChatListAdapter(
    val listchat:ArrayList<ChatItem>,
    val friends:ArrayList<UserItem>,
    val listdchat:ArrayList<UserChatItem>,
    val id:Int
):RecyclerView.Adapter<ChatListAdapter.VH>() {
    inner class VH(val v:View):RecyclerView.ViewHolder(v) {
        val tvnama:TextView=v.findViewById(R.id.tvChatListNama)
        val tvbody:TextView=v.findViewById(R.id.tvchatlistbody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.layout_chat_list,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c=listchat[position]
        val f= if (c.user_id==id) friends.find { u->u.id==c.recipient_id } else friends.find { u->u.id==c.user_id }
        val dc=listdchat.filter { u->u.chat_id==c.id }
        with(holder){
            tvnama.text=f!!.name
            tvbody.text=if(dc.isEmpty()) "" else dc.last().message
        }
    }

    override fun getItemCount(): Int {
        return listchat.size
    }
}