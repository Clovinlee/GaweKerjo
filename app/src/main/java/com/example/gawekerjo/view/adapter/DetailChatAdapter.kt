package com.example.gawekerjo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.model.userchat.UserChatItem
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class DetailChatAdapter(
    val chat:ArrayList<UserChatItem>,
    val user_id:Int
):RecyclerView.Adapter<DetailChatAdapter.VH>() {
    class VH(val v:View):RecyclerView.ViewHolder(v) {
        val tv=v.findViewById<TextView>(R.id.tvtglchat)
        val lluser=v.findViewById<LinearLayout>(R.id.lluser)
        val llrec=v.findViewById<LinearLayout>(R.id.llrecipient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.layout_chat,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c=chat[position]
        var parser=SimpleDateFormat("yyyy-MM-dd")
        var formatter=SimpleDateFormat("EEEE, dd/MM/yyyy")
        val tanggal=formatter.format(parser.parse(c.created_at.substring(0,c.created_at.indexOf("T"))))
        val yesterday=Date()
        yesterday.date--
        val tgl=if (formatter.format(Date())==tanggal) "Today" else if(formatter.format(yesterday)==tanggal) "Yesterday" else tanggal
        with(holder){
            if(c==chat.first()||tanggal!=formatter.format(parser.parse(chat[position-1].created_at.substring(0,c.created_at.indexOf("T"))))){
                tv.visibility=View.VISIBLE
                tv.text=tgl
            }
            parser=SimpleDateFormat("HH:mm")
            formatter=SimpleDateFormat("KK:mm aaa")
            if (c.user_id==user_id){
                llrec.visibility=View.GONE
                val tvmessage=v.findViewById<TextView>(R.id.tvchatisi)
                val tvjam=v.findViewById<TextView>(R.id.tvchatjam)
                tvmessage.text=c.message
                val jam=parser.parse(c.created_at.substring(c.created_at.indexOf("T")+1,c.created_at.indexOf("T")+6))
                jam.hours+=7
                tvjam.text=formatter.format(jam)
            }else{
                lluser.visibility=View.GONE
                val tvmessage=v.findViewById<TextView>(R.id.tvchatisirecipient)
                val tvjam=v.findViewById<TextView>(R.id.tvchatjamrecipient)
                tvmessage.text=c.message
                val jam=parser.parse(c.created_at.substring(c.created_at.indexOf("T")+1,c.created_at.indexOf("T")+6))
                jam.hours+=7
                tvjam.text=formatter.format(jam)
            }
        }
    }

    override fun getItemCount(): Int {
        return chat.size
    }
}