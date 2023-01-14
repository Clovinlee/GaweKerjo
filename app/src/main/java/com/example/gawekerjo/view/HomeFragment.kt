package com.example.gawekerjo.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.FragmentHomeBinding
import com.example.gawekerjo.model.post.PostItem
import com.example.gawekerjo.model.postlike.PostLikeItem
import com.example.gawekerjo.model.postlike.postLike
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.PostLikeRepository
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
    private lateinit var postlikeRepo : PostLikeRepository
    var arrPost = ArrayList<PostItem>()
    var arrPostLike = ArrayList<PostLikeItem>()

    private var firstFetch = true
    private var firstFetchlike = true

    lateinit var launcherDetail : ActivityResultLauncher<Intent>

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

        coroutine.launch {
            db.postDao.clear()
        }
        postRepo = PostRepository(db)
        postlikeRepo = PostLikeRepository(db)
        loadDataPost()
        loadDataLike()



        launcherDetail = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val data = it.data
            if(data != null){

            }
        }
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
            Log.d("CCD", arrPost.size.toString())
            if((arrPost.size == 0 && fetched == false) || firstFetch == true){
                firstFetch = false
                postRepo.getAllPostRelated(this@HomeFragment, mc.user.id)
                b.txtEmpty.visibility = View.INVISIBLE
                b.rvHome.visibility = View.VISIBLE
            }else{
                mc.runOnUiThread {
                    if(arrPost.size == 0 && fetched == true){
                        b.rvHome.visibility = View.INVISIBLE
                        b.txtEmpty.visibility = View.VISIBLE
                    }
                    loadDataLike()
                }
            }
        }
    }

    fun loadDataLike(fetched: Boolean = false){
        coroutine.launch {
            arrPostLike = db.postlikeDao.getAllPostLike() as ArrayList<PostLikeItem>
            if((arrPostLike.size == 0 && fetched == false) || firstFetchlike == true){
                firstFetchlike = false
                postlikeRepo.getPostLikes(this@HomeFragment, mc.user.id)
            }
            else{
                mc.runOnUiThread {
                    initPost()
                }
            }
        }
    }

    fun initPost(){
        postAdapter = PostAdapter(this, arrPost, arrPostLike, db, mc.user.id)
        b.rvHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        b.rvHome.adapter = postAdapter
    }

    fun addPostLikeCallBack(result : postLike){
        if(result.status == 200){
            Toast.makeText((context as HomeActivity), "${result.message}", Toast.LENGTH_SHORT).show()
            val i = Intent()
            (context as HomeActivity).setResult(1, i)
            (context as HomeActivity).finish()
        }
        else{
            Toast.makeText(context, "${result.message}", Toast.LENGTH_SHORT).show()
        }
    }

    //Belom ditambahkan
    fun deletecallback(result : postLike){
        if(result.status == 200){
            Toast.makeText(context, "${result.message}", Toast.LENGTH_SHORT).show()

            load()
        }
        else{
            Toast.makeText(context, "${result.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun load(){
        arrPostLike.clear()
        coroutine.launch {
            arrPostLike.addAll(db.postlikeDao.getAllPostLike())
        }
    }



    fun viewDetail(post_id: Int, user_id: Int){
        val intent = Intent(mc, DetailpostActivity::class.java)
        intent.putExtra("post_id", post_id)
        intent.putExtra("user_id", user_id)
        startActivity(intent)
    }

    fun masukprofil(item: UserItem){
        if (item.type == "1"){
            var i = Intent(requireContext(), UserprofileActivity::class.java)
            i.putExtra("userLogin", item)
            i.putExtra("Action", 1)
            startActivity(i)
        }
        else{
            var i = Intent(requireContext(), CompanyProfileActivity::class.java)
            i.putExtra("userLogin", item)
            i.putExtra("Action", 1)
            startActivity(i)
        }
    }
}