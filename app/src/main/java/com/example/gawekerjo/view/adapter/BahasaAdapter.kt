package com.example.gawekerjo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.model.userlanguage.UserLanguageItem

class BahasaAdapter (
    private val listbahasa: MutableList<UserLanguageItem>,
    private val layout: Int,
    private val context: Context,
    private val cb: ()->Unit
): RecyclerView.Adapter<BahasaAdapter.CustomViewHolder>(){

    private var onItemClickListener:OnRecyclerViewItemClickListener3?=null
    inner class CustomViewHolder(private val view:View):RecyclerView.ViewHolder(view) {
        val bahasa:TextView = itemView.findViewById(R.id.tvLayoutLIstBahasaBahasa)
        val level : TextView = itemView.findViewById(R.id.tvLayoutListBahasaLevel)
        val hapus: ImageView = itemView.findViewById(R.id.imageLayoutListBahasaHapus)
        init {
            hapus.setOnClickListener {
                onItemClickListener?.OnClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bahasa.text = listbahasa[position].language
        holder.level.text = "${listbahasa[position].level}"
    }

    override fun getItemCount(): Int {
        return listbahasa.size
    }

    fun setOnItemClickListener(onItemClickListener: OnRecyclerViewItemClickListener3){
        this.onItemClickListener = onItemClickListener
    }

}

interface OnRecyclerViewItemClickListener3 {
    fun OnClick(view:View, position: Int)
}
