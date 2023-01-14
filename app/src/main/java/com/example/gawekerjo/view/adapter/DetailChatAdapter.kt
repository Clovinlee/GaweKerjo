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
        var parser=SimpleDateFormat("yyyy-MM-dd hh:mm")
        var formatter=SimpleDateFormat("EEEE, dd/MM/yyyy")
        var tanggal_temp=parser.parse(c.created_at.substring(0,c.created_at.indexOf("T")+6).replace("T"," "))
        tanggal_temp.hours+=7
        val tanggal=formatter.format(tanggal_temp)
        val yesterday=Date()
        yesterday.date--
        yesterday.hours+=7
        val tgl=if (formatter.format(Date())==tanggal) "Today" else if(formatter.format(yesterday)==tanggal) "Yesterday" else tanggal
        with(holder){
            var prev:Date?=null
            if (c!=chat.first()){
                prev=parser.parse(chat[position-1].created_at.substring(0,c.created_at.indexOf("T")+6).replace("T"," "))
                prev.hours+=7
            }
            if(prev==null||tanggal!=formatter.format(prev)){
                tv.visibility=View.VISIBLE
                tv.text=tgl
            }
            formatter=SimpleDateFormat("hh:mm aaa")
            if (c.user_id==user_id){
                llrec.visibility=View.GONE
                val tvmessage=v.findViewById<TextView>(R.id.tvchatisi)
                val tvjam=v.findViewById<TextView>(R.id.tvchatjam)
                tvmessage.text=c.message
                tvjam.text=formatter.format(tanggal_temp)
            }else{
                lluser.visibility=View.GONE
                val tvmessage=v.findViewById<TextView>(R.id.tvchatisirecipient)
                val tvjam=v.findViewById<TextView>(R.id.tvchatjamrecipient)
                tvmessage.text=c.message
                tvjam.text=formatter.format(tanggal_temp)
            }
        }
    }

    override fun getItemCount(): Int {
        return chat.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}