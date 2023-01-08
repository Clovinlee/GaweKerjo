package com.example.gawekerjo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.model.education.EducationItem
import org.w3c.dom.Text

class PendidikanAdapter (
    private val listpendidikan : MutableList<EducationItem>,
    private val layout: Int,
    private val context: Context,
    private val cb: ()->Unit
): RecyclerView.Adapter<PendidikanAdapter.CustomViewHolder>(){

    private var onItemClickListener:OnRecyclerViewItemClickListener2?=null

    inner class CustomViewHolder(private val view:View):RecyclerView.ViewHolder(view){
        val nama:TextView = itemView.findViewById(R.id.tvLayoutListPendidikanNama)
        val tahun:TextView = itemView.findViewById(R.id.tvLayoutListPendidikanTahun)
        val nilai:TextView = itemView.findViewById(R.id.tvLayoutListPendidikanNilai)
        val hapus:ImageView = itemView.findViewById(R.id.imgLayoutListPendidikanHapus)
        init{
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
        holder.nama.text = listpendidikan[position].name
        holder.tahun.text = "${listpendidikan[position].date_start} sampai ${listpendidikan[position].date_end}"
        holder.nilai.text = listpendidikan[position].score
    }

    override fun getItemCount(): Int {
        return listpendidikan.size
    }

    fun setOnItemClickListener(onItemClickListener2: OnRecyclerViewItemClickListener2){
        this.onItemClickListener = onItemClickListener2
    }
}

interface OnRecyclerViewItemClickListener2 {
    fun OnClick(view:View, position: Int)
}
