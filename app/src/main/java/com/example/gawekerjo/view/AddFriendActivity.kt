package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import kotlinx.coroutines.launch

class AddFriendActivity : AppCompatActivity() {
    private lateinit var b: ActivityAddFriendBinding

    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase

    private lateinit var accFollow : FollowRepository
    private lateinit var followList :List<FollowItem>

    lateinit var user: UserItem

    private var userUnfriend : List<UserItem> = listOf()

    private lateinit var rv : RecyclerView
    private lateinit var AddFriendAdapter : AddFriendAdapter

    lateinit var btnSearch : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAddFriendBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)

        followList = listOf()

        accFollow = FollowRepository(db)
        btnSearch = findViewById(R.id.btnSearch)

        user=intent.getParcelableExtra("userlogin")!!
        followList=intent.getParcelableArrayListExtra<FollowItem>("followList")!!

        rv = findViewById(R.id.rvSearch)

        btnSearch.setOnClickListener()
        {
            initRV()
            var txtSearch = findViewById<EditText>(R.id.searchfriend)
            if (txtSearch.text.toString()=="")
            {
                accFollow.getUnfriendUser(this, user)
            }else
            {
                accFollow.searchuser(this,txtSearch.text.toString(),null)
            }
//            Toast.makeText(this, "ini kok gamau kepencet hadeh", Toast.LENGTH_SHORT).show()
            initRV()
        }
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        accFollow.getUnfriendUser(this, user)
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }

    fun callbackUnfriend(usr : User){
        userUnfriend = usr.data
        initRV()
    }

    fun addUser(itemid:Int?){
        accFollow.addFriends(this,user.id,itemid)
    }

    fun addFriendCallback(flw : Follow){
        accFollow.getUnfriendUser(this, user)
    }

    fun initRV()
    {
        AddFriendAdapter = AddFriendAdapter(this,userUnfriend)
        rv.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rv.adapter = AddFriendAdapter
        AddFriendAdapter.notifyDataSetChanged()
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


    fun masukprofil(item: UserItem){
        if (item.type == "1"){
            var i = Intent(this, UserprofileActivity::class.java)
            i.putExtra("userLogin", item)
            i.putExtra("Action", 1)
            startActivity(i)
        }
        else{
            var i = Intent(this, CompanyProfileActivity::class.java)
            i.putExtra("userLogin", item)
            i.putExtra("Action", 1)
            startActivity(i)
        }
    }
}