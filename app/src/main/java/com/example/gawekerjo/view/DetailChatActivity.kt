package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityDetailChatBinding
import com.example.gawekerjo.model.chat.ChatItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.model.userchat.UserChatItem
import com.example.gawekerjo.repository.ChatRepository
import com.example.gawekerjo.view.adapter.DetailChatAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailChatActivity : AppCompatActivity() {
    lateinit var b:ActivityDetailChatBinding
    lateinit var db: AppDatabase
    lateinit var cr: ChatRepository
    lateinit var user:UserItem
    lateinit var recipient:UserItem
    lateinit var hchat:ChatItem
    lateinit var chatlist:ArrayList<UserChatItem>
    lateinit var newchatlist:ArrayList<UserChatItem>
    val c=CoroutineScope(Dispatchers.IO)
    lateinit var adapter:DetailChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=ActivityDetailChatBinding.inflate(layoutInflater)
        val v=b.root
        setContentView(v)
        db=AppDatabase.Build(this)
        cr= ChatRepository(db)
        user=intent.getParcelableExtra("user")!!
        recipient=intent.getParcelableExtra("rec")!!
        title=recipient.name.uppercase()
        hchat= intent.getParcelableExtra("hchat")!!
        chatlist=intent.getParcelableArrayListExtra("chat")!!
        newchatlist= arrayListOf()
        adapter= DetailChatAdapter(chatlist,user.id)
        with(b){
            rvdetailchat.adapter=adapter
            rvdetailchat.layoutManager=LinearLayoutManager(this@DetailChatActivity,LinearLayoutManager.VERTICAL,false)
            //rvdetailchat.layoutManager?.scrollToPosition(chatlist.lastIndex)
            btnsendchat.setOnClickListener {
                val txt=etchat.text.toString()
                c.launch {
                    cr.addChat(this@DetailChatActivity,user.id,hchat.id,txt)
                }
            }
        }
    }

    fun Chat(c: UserChatItem) {
        newchatlist.add(c)
        chatlist.add(c)
        runOnUiThread {
            adapter.notifyDataSetChanged()
            b.rvdetailchat.layoutManager?.scrollToPosition(chatlist.lastIndex)
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val i=Intent()
        i.putParcelableArrayListExtra("newchat",newchatlist)
        setResult(RESULT_OK,i)
        finish()
    }
}