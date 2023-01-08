package com.example.gawekerjo.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.api.UserApi
import com.example.gawekerjo.model.follow.FollowItem
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.FollowRepository
import com.example.gawekerjo.view.AddFriendActivity
import com.example.gawekerjo.view.FriendListActivity
import com.example.gawekerjo.view.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class FollowAdapter2 (
    val followList :List<FollowItem>,
    var HomeActivity : HomeActivity,
    var user: UserItem,
): RecyclerView.Adapter<FollowAdapter2.CustomViewHolder>() {

    private val rc : Retrofit = RetrofitClient.getRetrofit()

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
                    holder.txtNameFriendList.text = rbody.data[0].name
                    holder.txtDescFriendList.text = rbody.data[0].description
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CCD","User not found")
            }
        })

//        Log.d("CCD", "Ini nyoba di adapter size e : " + followList.size.toString())
        holder.btnAddFriend.setOnClickListener()
        {
            // 1. MC.hapusFriend(idFollow)
            // 2. MC -> Panggil ke repo
            // 3. Repo -> Kamu panggil callback ke MC
            // 4. INIT RV ULANG

            // ===
            // 1. LIST E SAMA MBE SENG DI PASS DI RV
            // 2. teliti
//            HomeActivity.removeFollow(item.id)
        }

        holder.btnMessageFriendList.setOnClickListener()
        {
//            BAGIAN KWAN
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
    }
}