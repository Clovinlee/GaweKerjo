package com.example.gawekerjo.view.adapter

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.api.UserApi
import com.example.gawekerjo.env
import com.example.gawekerjo.model.follow.FollowItem
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.FollowRepository
import com.example.gawekerjo.view.AddFriendActivity
import com.example.gawekerjo.view.FriendListActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.net.URL

class FollowAdapter (
    val followList :List<FollowItem>,
    var AddFriendActivity : FriendListActivity,
    var user: UserItem,
    val chat: (recipient:UserItem)->Unit
): RecyclerView.Adapter<FollowAdapter.CustomViewHolder>() {

    private val rc : Retrofit = RetrofitClient.getRetrofit()
    private val coroutine = CoroutineScope(Dispatchers.IO)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            R.layout.layout_friend_list, parent ,false
        ))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = followList[position]

        var idUser = item.user_id
        var idUser2 = item.follow_id

        var idFollow = idUser
        if(idUser == user.id){
            idFollow = idUser2
        }

        var rc_user : Call<User> = rc.create(UserApi::class.java).getUser(idFollow,null,null)

        rc_user.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                var rbody = response.body()!!
                if(rbody.status == 200){
                    //val recipient=rbody.data.first()
                    holder.txtNameFriendList.text = rbody.data[0].name
                    holder.txtDescFriendList.text = rbody.data[0].description
                    holder.btnMessageFriendList.setOnClickListener()
                    {
                        //BAGIAN KWAN
                        Log.d("tes","bisa")
                        chat.invoke(rbody.data.first())
                    }
                    coroutine.launch {
                        if (rbody.data[0].image!=null){
                            val i= URL(env.API_URL.substringBefore("/api/")+rbody.data[0].image).openStream()
                            val image= BitmapFactory.decodeStream(i)
                            AddFriendActivity.runOnUiThread{
                                holder.imgFriend.setImageBitmap(image)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CCD","User not found")
            }
        })

        holder.btnAddFriend.setOnClickListener()
        {
            // 1. MC.hapusFriend(idFollow)
            // 2. MC -> Panggil ke repo
            // 3. Repo -> Kamu panggil callback ke MC
            // 4. INIT RV ULANG

            // ===
            // 1. LIST E SAMA MBE SENG DI PASS DI RV
            // 2. teliti
            AddFriendActivity.removeFollow(item.id)
        }

    }

    // digunakan untuk mengetahui ukuran dari list view yang akan di iterasikan
    override fun getItemCount(): Int {
        return followList.size
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtNameFriendList: TextView = itemView.findViewById(R.id.txtNameAddFriend)
        val txtDescFriendList: TextView = itemView.findViewById(R.id.txtDescAddFriend)
        val btnMessageFriendList: ImageView = itemView.findViewById(R.id.btnMessage)
        val btnAddFriend: ImageView = itemView.findViewById(R.id.btnAddFriend)
        val imgFriend : ImageView = itemView.findViewById(R.id.imgProfileFriendList)
    }
}