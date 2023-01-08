package com.example.gawekerjo.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.model.follow.FollowItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.FollowRepository
import com.example.gawekerjo.view.AddFriendActivity

class AddFriendAdapter(
    var AddFriendActivity : AddFriendActivity,
    val userUnfriendList :List<UserItem>,
): RecyclerView.Adapter<AddFriendAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            R.layout.layout_add_friend, parent ,false
        ))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = userUnfriendList[position]
        holder.txtNameAddFriend.text = item.name
        holder.txtDescAddFriend.text = item.description

        holder.btnAddFriend.setOnClickListener()
        {
//            accFollow.addFriends(AddFriendActivity,item.id,user.id)
            holder.btnAddFriend.setImageResource(R.drawable.ic_baseline_done_24)
            holder.btnAddFriend.setTag(R.drawable.ic_baseline_done_24)

            AddFriendActivity.addUser(item.id)
        }

    }

    // digunakan untuk mengetahui ukuran dari list view yang akan di iterasikan
    override fun getItemCount(): Int {
        return userUnfriendList.size
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtNameAddFriend: TextView = itemView.findViewById(R.id.txtNameAddFriend)
        val txtDescAddFriend: TextView = itemView.findViewById(R.id.txtDescAddFriend)
        val btnAddFriend: ImageView = itemView.findViewById(R.id.btnMessage)
    }
}