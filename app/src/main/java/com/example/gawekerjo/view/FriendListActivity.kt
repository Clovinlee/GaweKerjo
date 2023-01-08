package com.example.gawekerjo.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityFriendListBinding
import com.example.gawekerjo.model.follow.Follow
import com.example.gawekerjo.model.follow.FollowItem
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.FollowRepository
import com.example.gawekerjo.view.adapter.FollowAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendListActivity : AppCompatActivity() {

    private lateinit var b: ActivityFriendListBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private lateinit var accFollow : FollowRepository
    private lateinit var followList :ArrayList<FollowItem>
    lateinit var user: UserItem
    private lateinit var rv : RecyclerView
    private lateinit var FollowAdapter : FollowAdapter
    lateinit var allUser : ArrayList<UserItem>

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if (result.resultCode == 1){
//            followList.clear()
            coroutine.launch {
//                followList.addAll(db.userskillDao.getAllUserSkill().toList())
            }
//            keahlianAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)
        b = ActivityFriendListBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        db = AppDatabase.Build(this)
        followList = ArrayList()
        allUser = ArrayList()
        accFollow = FollowRepository(db)
        user=intent.getParcelableExtra("userlogin")!!
        accFollow.getFriends(this,null,user.id,null)
        accFollow.getUser(this,null,null,null)
        rv = findViewById(R.id.rv)

//        Toast.makeText(this, followList.size.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menuaddfriend,menu)
        return super.onCreateOptionsMenu(menu)
    }

    // method yang digunakan bila ada option menu yang terklik
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuaddfriend->{
                val intent = Intent(this,AddFriendActivity::class.java)
                intent.putParcelableArrayListExtra("allUser", allUser)
                intent.putParcelableArrayListExtra("followList", followList)
                intent.putExtra("userlogin", user)
//                addLauncher.launch(intent)
                startActivity(intent)
                true
            }
            else->{
                false
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun refresh(result : Follow){
        if(result.data.size > 0){
            val flw = result.data
            followList = flw as ArrayList<FollowItem>
            FollowAdapter = FollowAdapter(followList,allUser,accFollow,this,user)
//            AddFriendAdapter = AddFriendAdapter(followList,allUser,accFollow,this,user)
            rv.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            rv.adapter = FollowAdapter
            FollowAdapter.notifyDataSetChanged()
        }else{
            runOnUiThread {
                Toast.makeText(this, "Gagal dapatkan followers", Toast.LENGTH_SHORT).show()
            }
        }
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
        }else{
            runOnUiThread {
                Toast.makeText(this, "Gagal dapatkan followers", Toast.LENGTH_SHORT).show()
            }
        }
    }
}