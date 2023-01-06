package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.gawekerjo.R
import com.example.gawekerjo.model.follow.Follow
import com.example.gawekerjo.repository.FollowRepository

class FriendListActivity : AppCompatActivity() {

    private lateinit var followRepo : FollowRepository;
    private lateinit var followList : Follow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menuaddfriend,menu)
        return super.onCreateOptionsMenu(menu)
    }

    // method yang digunakan bila ada option menu yang terklik
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuaddfriend->{
//                val intent = Intent(this,FavoriteActivity::class.java)
//                intent.putParcelableArrayListExtra("arrBoy", arrBoyName)
//                intent.putParcelableArrayListExtra("arrGirl", arrGirlName)
//                addLauncher.launch(intent)
                true
            }
            else->{
                false
            }
        }

        return super.onOptionsItemSelected(item)
    }
}