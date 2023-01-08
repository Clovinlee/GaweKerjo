package com.example.gawekerjo.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.model.follow.FollowItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.FollowRepository
import com.example.gawekerjo.view.AddFriendActivity

class AddFriendAdapter(
    val followList :List<FollowItem>,
    val allUser : List<UserItem>,
    var accFollow : FollowRepository,
    var AddFriendActivity : AddFriendActivity,
    var user: UserItem
): RecyclerView.Adapter<AddFriendAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            R.layout.layout_add_friend, parent ,false
        ))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = allUser[position]
        holder.txtNameAddFriend.text = item.name
        holder.txtDescAddFriend.text = item.description
        var ketemu = -1;
        for (i in followList)
        {
            if (i.follow_id == item.id)
            {
                ketemu = 1
            }
        }
        if (ketemu==1)
        {
//            holder.btnAddFriend.isEnabled = false
            holder.btnAddFriend.setImageResource(R.drawable.ic_baseline_done_24)
            holder.btnAddFriend.setTag(R.drawable.ic_baseline_done_24)
            Log.d("CCD","masuk sih ini")
        }
        holder.btnAddFriend.setOnClickListener()
        {
            if (holder.btnAddFriend.getTag() == R.drawable.ic_baseline_done_24)
            {
                accFollow.removefollows(AddFriendActivity,item.id)
                holder.btnAddFriend.setImageResource(R.drawable.ic_baseline_add_24)
                holder.btnAddFriend.setTag(R.drawable.ic_baseline_add_24)
            }else{
                accFollow.addFriends(AddFriendActivity,item.id,user.id)
                holder.btnAddFriend.setImageResource(R.drawable.ic_baseline_done_24)
                holder.btnAddFriend.setTag(R.drawable.ic_baseline_done_24)
            }
        }
    }

    // digunakan untuk mengetahui ukuran dari list view yang akan di iterasikan
    override fun getItemCount(): Int {
        return allUser.size
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtNameAddFriend: TextView = itemView.findViewById(R.id.txtNameAddFriend)
        val txtDescAddFriend: TextView = itemView.findViewById(R.id.txtDescAddFriend)
        val btnAddFriend: ImageView = itemView.findViewById(R.id.btnAddFriend)
    }
}