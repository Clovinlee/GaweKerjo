package com.example.gawekerjo.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.database.ChatDao
import com.example.gawekerjo.databinding.ActivityChatBinding
import com.example.gawekerjo.model.chat.ChatItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.model.userchat.UserChatItem
import com.example.gawekerjo.repository.ChatRepository
import com.example.gawekerjo.view.adapter.ChatAddSearchAdapter
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
    lateinit var listfriend: ArrayList<UserItem>
    val c= CoroutineScope(Dispatchers.IO)
    lateinit var chatlist:ArrayList<UserChatItem>
    lateinit var new_friend:ArrayList<UserItem>
    lateinit var new_friend_dataset:ArrayList<UserItem>
    lateinit var adaptersearch:ChatAddSearchAdapter
    lateinit var d:Dialog
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
        listfriend: ArrayList<UserItem>,
        newfriends: ArrayList<UserItem>
    ){
        listchat=lc
        new_friend=newfriends
        this.listfriend=listfriend
        this.chatlist=listdchat
        runOnUiThread {
            adapter= ChatListAdapter(this.listfriend,listchat,chatlist,user.id)
            new_friend_dataset= arrayListOf()
            new_friend_dataset.addAll(new_friend)
            adaptersearch= ChatAddSearchAdapter(new_friend_dataset){
                c.launch {
                    cr.newChat(user.id,it.id,this@ChatActivity)
                    d.dismiss()
                }
            }
            with(b){
                rvchatlist.adapter=adapter
                rvchatlist.layoutManager=LinearLayoutManager(this@ChatActivity,LinearLayoutManager.VERTICAL,false)
                ChatLoadModal.visibility=View.GONE
            }
        }
        loaded=true
    }
    fun newChat(c:ChatItem){
        runOnUiThread {
            val f=new_friend.find { n->n.id==c.recipient_id }
            if (f != null) {
                listfriend.add(f)
                listchat.add(c)
                adapter.notifyDataSetChanged()
            }
            new_friend.remove(f)
            adaptersearch.notifyDataSetChanged()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_chat,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.tambah_chat->{
                if (loaded){
                    new_friend_dataset.clear()
                    new_friend_dataset.addAll(new_friend)
                    adaptersearch.notifyDataSetChanged()
                    Dialog()
                }else{
                    Toast.makeText(this, "Please wait until all messages has been loaded", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun Dialog() {
        val binding=layoutInflater.inflate(R.layout.chat_dialog_layout,null)
        d= Dialog(this)
        with(d) {
            setContentView(binding)
            setCancelable(true)
            val txt=binding.findViewById<EditText>(R.id.etchataddsearch)
            txt.doOnTextChanged { text, start, before, count ->
                new_friend_dataset.clear()
                val temp=if (count>0) new_friend.filter { n->n.name.contains(text.toString(),ignoreCase = true) } else new_friend
                new_friend_dataset.addAll(temp)
                adaptersearch.notifyDataSetChanged()
            }
            val rv=binding.findViewById<RecyclerView>(R.id.rvaddchatsearch)
            rv.adapter=adaptersearch
            rv.layoutManager=LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
            show()
        }
    }
}