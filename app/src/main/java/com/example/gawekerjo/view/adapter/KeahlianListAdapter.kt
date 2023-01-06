package com.example.gawekerjo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.model.skill.SkillItem
import com.example.gawekerjo.model.userskill.UserSkillItem
import java.text.FieldPosition

class KeahlianListAdapter (
    private val listkeahlian: MutableList<UserSkillItem>,
    private val listnama: MutableList<SkillItem>,
    private val layout: Int,
    private val context: Context,
    private val cb: ()->Unit
) : RecyclerView.Adapter<KeahlianListAdapter.CustomViewHolder>(){

    private  var onItemCLickListener:OnRecyclerViewItemClickListener?=null

    inner class  CustomViewHolder(private  val view: View):RecyclerView.ViewHolder(view){
        val keahlian: TextView = itemView.findViewById(R.id.tvLayoutLIstKeahlianKeahlian)
        val edit: ImageView = itemView.findViewById(R.id.imageLayoutListKeahlianEdit)
        init {
            edit.setOnClickListener {
                onItemCLickListener?.OnClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

            for (k in 0 until listnama.size){
                if (listkeahlian[position].skill_id == listnama[k].id){
                    holder.keahlian.text = listnama[position].name.toString()
                }
            }


    }

    override fun getItemCount(): Int {
        return  listkeahlian.size
    }


}

interface OnRecyclerViewItemClickListener {
    fun OnClick(view:View, position: Int)
}
