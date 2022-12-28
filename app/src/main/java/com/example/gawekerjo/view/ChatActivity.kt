package com.example.gawekerjo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.database.ChatDao
import com.example.gawekerjo.databinding.ActivityChatBinding
import com.example.gawekerjo.model.chat.ChatItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.model.userchat.UserChatItem
import com.example.gawekerjo.repository.ChatRepository
import com.example.gawekerjo.view.adapter.ChatListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {
    lateinit var b:ActivityChatBinding
    lateinit var user:UserItem
    lateinit var db:AppDatabase
    lateinit var cr:ChatRepository
    lateinit var adapter:ChatListAdapter
    lateinit var listchat:ArrayList<ChatItem>
    val c= CoroutineScope(Dispatchers.IO)
    lateinit var chatlist:ArrayList<ChatItem>
    var loaded=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=ActivityChatBinding.inflate(layoutInflater)
        val v=b.root
        setContentView(v)
        user=intent.getParcelableExtra("userlogin")!!
        db=AppDatabase.Build(this)
        cr= ChatRepository(db)
        c.launch {
            cr.getChat(user.id,this@ChatActivity)
        }
    }
    fun Start(
        lc: ArrayList<ChatItem>,
        listdchat: ArrayList<UserChatItem>,
        listfriend: ArrayList<UserItem>
    ){
        listchat=lc
        runOnUiThread {
            adapter= ChatListAdapter(listchat,listfriend,listdchat,user.id)
            with(b){
                rvchatlist.adapter=adapter
                rvchatlist.layoutManager=LinearLayoutManager(this@ChatActivity,LinearLayoutManager.VERTICAL,false)
                ChatLoadModal.visibility=View.GONE
            }
        }
        loaded=true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_chat,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.tambah_chat->{
                if (loaded){
                    
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}