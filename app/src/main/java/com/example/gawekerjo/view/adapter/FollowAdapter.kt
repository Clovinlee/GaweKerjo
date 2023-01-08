package com.example.gawekerjo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.model.follow.FollowItem
import com.example.gawekerjo.model.user.UserItem

class FollowAdapter (
    val followList :List<FollowItem>,
    val allUser : List<UserItem>
        ): RecyclerView.Adapter<FollowAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            R.layout.layout_friend_list, parent ,false
        ))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = followList[position]
        var idUser = item.follow_id
        var namaUser = item.user_id.toString()
        var desk = item.follow_id.toString()
        for (i in allUser)
        {
            if (i.id == idUser)
            {
                namaUser = i.name.toString()
                desk = i.description.toString()
            }
        }
        holder.txtNameFriendList.text = namaUser
        holder.txtDescFriendList.text = desk
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
        val btnMessageFriendList: ImageView = itemView.findViewById(R.id.btnAddFriend)
    }
}