package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityAddFriendBinding
import com.example.gawekerjo.databinding.ActivityFriendListBinding
import com.example.gawekerjo.model.follow.Follow
import com.example.gawekerjo.model.follow.FollowItem
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.FollowRepository
import com.example.gawekerjo.view.adapter.AddFriendAdapter
import com.example.gawekerjo.view.adapter.FollowAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AddFriendActivity : AppCompatActivity() {
    private lateinit var b: ActivityAddFriendBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private lateinit var accFollow : FollowRepository
    private lateinit var followList :List<FollowItem>
    lateinit var user: UserItem
    private lateinit var rv : RecyclerView
    private lateinit var AddFriendAdapter : AddFriendAdapter
    lateinit var allUser : ArrayList<UserItem>
    lateinit var btnSearch : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        b = ActivityAddFriendBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        db = AppDatabase.Build(this)

        followList = listOf()
        allUser = ArrayList()

        accFollow = FollowRepository(db)
        btnSearch = findViewById(R.id.btnSearch)
        user=intent.getParcelableExtra("userlogin")!!
        allUser=intent.getParcelableArrayListExtra<UserItem>("allUser")!!
        followList=intent.getParcelableArrayListExtra<FollowItem>("followList")!!
        Log.d("CCD","jumlah temen : "+ followList.size.toString())
        accFollow.getUser2(this,null,null,null)
        rv = findViewById(R.id.rvSearch)
    }

    fun getAll(result : User){
//        b.loadModal.visibility = View.INVISIBLE
        if(result.data.size > 0){
            val usr = result.data
            allUser = usr as ArrayList<UserItem>
//            val i : Intent = Intent(this, HomeActivity::class.java)
//            i.putExtra("userlogin", usr)
//            startActivity(i)
//            this.finish()
//            Toast.makeText(this, followList.size.toString(), Toast.LENGTH_SHORT).show()
//            FollowAdapter = FollowAdapter(followList)
//            rv.adapter = FollowAdapter
            var ketemu = -1
            for (i in 0 until allUser.size)
            {
                if (allUser[i].id == user.id)
                {
                    ketemu = i
                }
            }
            if (ketemu!=-1)
            {
                allUser.removeAt(ketemu)
            }
            btnSearch.setOnClickListener()
            {
                var txtSearch = findViewById<EditText>(R.id.searchfriend)
                for (i in 0 until allUser.size)
                {
                    if (!allUser[i].name.toString().lowercase().contains(txtSearch.text.toString().lowercase()))
                    {
                        allUser.removeAt(i)
                    }
                }
            }
            AddFriendAdapter = AddFriendAdapter(followList,allUser,accFollow,this,user)
            rv.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            rv.adapter = AddFriendAdapter
            AddFriendAdapter.notifyDataSetChanged()
        }else{
            runOnUiThread {
                Toast.makeText(this, "Gagal dapatkan followers", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addFriend(result : Follow){
//        b.loadModal.visibility = View.INVISIBLE
        if(result.status == 200){
//            var i : Intent = Intent(this, HomeActivity::class.java)
//            i.putExtra("userlogin",result.data[0])
//            startActivity(i)
//            this.finish()
        }else{
            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
        }
    }
}