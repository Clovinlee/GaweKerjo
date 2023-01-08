package com.example.gawekerjo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.FragmentHomeBinding
import com.example.gawekerjo.model.post.PostItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.PostRepository
import com.example.gawekerjo.view.adapter.PostAdapter
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment(var mc: HomeActivity, var db : AppDatabase, var user : UserItem) : Fragment() {
    lateinit var b : FragmentHomeBinding
    lateinit var postAdapter : PostAdapter
    private  val coroutine =  CoroutineScope(Dispatchers.IO)
    private lateinit var postRepo : PostRepository
    var arrPost = ArrayList<PostItem>()

    private var firstFetch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentHomeBinding.inflate(inflater, container, false)
        val v = b.root
        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postRepo = PostRepository(db)
        loadDataPost()
    }

//    fun refreshview(){
//        arrPost.clear()
//        postRepo.getAllPostRelated(mc, mc.user.id)
//        coroutine.launch {
//            arrPost = db.postDao.getAllPost() as ArrayList<PostItem>
//        }
//        postAdapter = PostAdapter(this, )
//    }

    fun loadDataPost(fetched : Boolean = false){
        coroutine.launch {
            arrPost = db.postDao.getAllPost() as ArrayList<PostItem>
            if((arrPost.size == 0 && fetched == false) || firstFetch == true){
                firstFetch = false
                postRepo.getAllPostRelated(this@HomeFragment, mc.user.id)
            }else{
                mc.runOnUiThread {
                    initPost()
                }
            }
        }
    }

    fun initPost(){
        postAdapter = PostAdapter(this, arrPost, db)
        b.rvHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        b.rvHome.adapter = postAdapter
    }
}