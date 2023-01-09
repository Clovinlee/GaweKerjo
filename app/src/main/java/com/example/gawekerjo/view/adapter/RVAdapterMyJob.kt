package com.example.gawekerjo.view.adapter

import android.graphics.BitmapFactory
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
import com.example.gawekerjo.env
import com.example.gawekerjo.model.Offer.OfferItem
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.view.MyOfferActivity
import com.example.gawekerjo.view.OffersFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.net.URL

class RVAdapterMyJob(private val mc : MyOfferActivity,
                     private val layout : Int,
                     private val listOffer : List<OfferItem>,
                     private val user : UserItem,
                     private val db : AppDatabase) : RecyclerView.Adapter<RVAdapterMyJob.CustomViewHolder>(){

    private val coroutine = CoroutineScope(Dispatchers.IO)

    class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view)
    {
        //DECLARE VARIABLE
        var imgOffer : ImageView = view.findViewById(R.id.imgOffer)
        var txtTitle : TextView = view.findViewById(R.id.txtRvJobTitle)
        var txtPostUser : TextView = view.findViewById(R.id.txtRvJobUser)
        var txtSkill : TextView = view.findViewById(R.id.txtRvJobSkills)
        var txtDescription : TextView = view.findViewById(R.id.txtRvJobDescription)

        var lin : LinearLayout = view.findViewById(R.id.linearLayoutJob)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(
            itemView.inflate(
                layout, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = listOffer[position]

        holder.txtTitle.text = item.title
        holder.txtDescription.text = item.body
        holder.txtSkill.text = item.skills
        holder.txtPostUser.text = user.name

        //holder.imgOffer

        coroutine.launch {
            if (user.image!=null){
                val i= URL(env.API_URL.substringBefore("/api/")+user.image).openStream()
                val image= BitmapFactory.decodeStream(i)
                mc.runOnUiThread {
                    holder.imgOffer.setImageBitmap(image)
                }
            }
        }

        holder.lin.setOnClickListener {
            mc.dialog(item, user)
        }
    }

    override fun getItemCount(): Int {
        return listOffer.size
    }
}