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
import com.example.gawekerjo.model.Offer.OfferItem
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.view.OffersFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RVAdapterJob(private val mc : OffersFragment,
                   private val layout : Int,
                   private val listOffer : List<OfferItem>,
                   private val db : AppDatabase) : RecyclerView.Adapter<RVAdapterJob.CustomViewHolder>(){

    private var loadDone : Boolean = false
    private lateinit var user : UserItem

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
        //holder.imgOffer

        var rc : Retrofit = RetrofitClient.getRetrofit()
        var rc_user : Call<User> = rc.create(UserApi::class.java).getUser(item.user_id, null, null)

        rc_user.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                var rbody = response.body()!!
                if(rbody.status == 200){
                    holder.txtPostUser.text = rbody.data[0].name
                    user = rbody.data[0]
                    loadDone = true
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CCD", "Error getting user getUser UserRepo")
                Log.d("CCD", t.message.toString())
                Log.d("CCD", "===============================")
            }

        })

        holder.lin.setOnClickListener {
            Log.d("CCD","PRESSED")
            Log.d("CCD",loadDone.toString())
            if(!loadDone){
                return@setOnClickListener
            }
            mc.dialog(item,user)
        }
    }

    override fun getItemCount(): Int {
        return listOffer.size
    }
}