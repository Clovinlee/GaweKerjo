package com.example.gawekerjo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityDetailpostBinding
import com.example.gawekerjo.model.post.PostItem
import com.example.gawekerjo.model.postlike.PostLikeItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.PostLikeRepository
import com.example.gawekerjo.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailpostActivity : AppCompatActivity() {
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var postRepo : PostRepository
    private lateinit var postlikeRepo : PostLikeRepository
    private lateinit var db : AppDatabase
    lateinit var b : ActivityDetailpostBinding

    private var user_id = 0
    private var post_id = 0


    private var arrPost = ArrayList<PostItem>()
    private var arrPostLike = ArrayList<PostLikeItem>()
    private var arrUser = ArrayList<UserItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailpost)

        b = ActivityDetailpostBinding.inflate(layoutInflater)
        val view  = b.root
        setContentView(view)

        db = AppDatabase.Build(this)
        postRepo = PostRepository(db)
        postlikeRepo = PostLikeRepository(db)

        try{
            user_id = intent.getIntExtra("user_id", 0)
            post_id = intent.getIntExtra("post_id", 0)
        }
        catch(e: Exception){
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }

        coroutine.launch {
           var arrPost = db.postDao.getPostById(post_id) as PostItem
           var arrUser = db.userDao.getUserById(user_id) as UserItem
            b.txtTitle.setText(arrPost.title)
            b.txtKeterangan.setText(arrPost.body)
            b.txtUsername.setText(arrUser.name)
        }

        b.btnAnswer.setText("Comment")
        b.btnAnswer.setOnClickListener {

        }


    }
}