package com.example.gawekerjo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityNewPostBinding
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewPostActivity : AppCompatActivity() {

    private lateinit var b : ActivityNewPostBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var postrepo : PostRepository
    private lateinit var user : UserItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        b = ActivityNewPostBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)

        postrepo = PostRepository(db)

        try {
            user = intent.getParcelableExtra<UserItem>("userlogin")!!
        }catch (e:Exception){
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }

        


    }
}