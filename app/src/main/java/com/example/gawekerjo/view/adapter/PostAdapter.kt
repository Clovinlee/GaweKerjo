package com.example.gawekerjo.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.api.UserApi
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.post.PostItem
import com.example.gawekerjo.model.postlike.PostLikeItem
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.PostLikeRepository
import com.example.gawekerjo.view.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PostAdapter(
    var mc : HomeFragment,
    var data: ArrayList<PostItem>,
    var dataLike: ArrayList<PostLikeItem>,
    var db : AppDatabase,
    var id: Int,

) : RecyclerView.Adapter<PostAdapter.MyHolder>(){

    val coroutine = CoroutineScope(Dispatchers.IO)
    lateinit var postlike : PostLikeRepository
    private lateinit var user : UserItem
    private var loadDone : Boolean = false

    class MyHolder(it: View) : RecyclerView.ViewHolder(it){
        var title: TextView = it.findViewById(R.id.txtUser)
        var content: TextView = it.findViewById(R.id.txtContent)
        var ctrlike: TextView = it.findViewById(R.id.txtLike)
        var ctrcomment: TextView = it.findViewById(R.id.txtComment)
        var imglike : ImageView = it.findViewById(R.id.imgLike)
        var imgcomment: ImageView = it.findViewById(R.id.imgComment)

        //Untuk mengarahkan ke halaman user
        var imgUser : ImageView = it.findViewById(R.id.imgProfileFriendList)

        var txtNama : TextView = it.findViewById(R.id.txtNama)
        var txtDeskripsiUser : TextView = it.findViewById(R.id.txtDeskripsiUser)

        var layout : LinearLayout = it.findViewById(R.id.LayoutPost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        postlike = PostLikeRepository(db)
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_post, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var itemView = data[position]

        holder.title.text = itemView.title
        holder.content.text = itemView.body
        holder.ctrlike.text = itemView.like_count.toString()

        var rc : Retrofit = RetrofitClient.getRetrofit()
        var rc_user : Call<User> = rc.create(UserApi::class.java).getUser(itemView.user_id, null, null)

        rc_user.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                var rbody = response.body()!!
                if(rbody.status == 200){
                    holder.txtNama.text = rbody.data[0].name
                    holder.txtDeskripsiUser.text = rbody.data[0].description
                    user = rbody.data[0]
                    loadDone = true

//                    coroutine.launch {
//                        if (user.image!=null){
//                            val i= URL(env.API_URL.substringBefore("/api/")+user.image).openStream()
//                            val image= BitmapFactory.decodeStream(i)
//                            mc.mc.runOnUiThread {
//                                holder.imgOffer.setImageBitmap(image)
//                            }
//                        }
//                    }

                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CCD", "Error getting user getUser UserRepo")
                Log.d("CCD", t.message.toString())
                Log.d("CCD", "===============================")
            }

        })

        var flag = 0
        for(i in 0..dataLike.size - 1) {
            if(dataLike[i].post_id == itemView.id)
            { flag = 1 }
        }
//        println("cek flag = " + itemView.id.toString() + " --- " + flag.toString())

        if(flag == 0) {
            holder.imglike.setImageResource(R.drawable.ic_outline_thumb_up_24)
        }
        else {
            holder.imglike.setImageResource(R.drawable.ic_baseline_thumb_up_24)
        }

        holder.imglike.setOnClickListener(View.OnClickListener {
            var flagdalam = 0
            for(i in 0..dataLike.size - 1) {
                if(dataLike[i].post_id == itemView.id)
                { flagdalam = 1 }
            }

            if(flagdalam == 0) {
                postlike.addPostLike(mc, id, itemView.id.toString().toInt())
                holder.imglike.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                dataLike.add(PostLikeItem(10, id, itemView.id, "", ""))
                holder.ctrlike.setText((holder.ctrlike.text.toString().toInt() + 1).toString())
            }
            else {
                postlike.removeLike(mc, id, itemView.id.toString().toInt())
                holder.imglike.setImageResource(R.drawable.ic_outline_thumb_up_24)
                var hapus = -1
                for (i in 0..dataLike.size -1){
                    if(dataLike[i].post_id == itemView.id){
                        hapus = i
                    }
                }
                dataLike.removeAt(hapus)
                holder.ctrlike.setText((holder.ctrlike.text.toString().toInt() - 1).toString())
            }
        })

//        holder.imgcomment.setOnClickListener {
//            mc.viewDetail(itemView.id, id)
//        }

        holder.layout.setOnClickListener {
            if(!loadDone){
                return@setOnClickListener
            }
            mc.viewDetail(itemView.id, id)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}