package com.example.gawekerjo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.model.userchat.UserChatItem
import kotlin.collections.ArrayList

class DetailChatAdapter(
    val chat:ArrayList<UserChatItem>,
    val user_id:Int
):RecyclerView.Adapter<DetailChatAdapter.VH>() {
    class VH(val v:View):RecyclerView.ViewHolder(v) {
        val lluser=v.findViewById<LinearLayout>(R.id.lluser)
        val llrec=v.findViewById<LinearLayout>(R.id.llrecipient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.layout_chat,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c=chat[position]
        with(holder){
            if (c.user_id==user_id){
                llrec.visibility=View.GONE
                val tvmessage=v.findViewById<TextView>(R.id.tvchatisi)
                val tvjam=v.findViewById<TextView>(R.id.tvchatjam)
                tvmessage.text=c.message
                tvjam.text=c.created_at.substring(c.created_at.indexOf("T")+1,c.created_at.indexOf("T")+6)
            }else{
                lluser.visibility=View.GONE
                val tvmessage=v.findViewById<TextView>(R.id.tvchatisirecipient)
                val tvjam=v.findViewById<TextView>(R.id.tvchatjamrecipient)
                tvmessage.text=c.message
                tvjam.text=c.created_at.substring(c.created_at.indexOf("T")+1,c.created_at.indexOf("T")+6)
            }
        }
    }

    override fun getItemCount(): Int {
        return chat.size
    }
}