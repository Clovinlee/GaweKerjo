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
    private val Action: Int,
    private val cb: ()->Unit
): RecyclerView.Adapter<PendidikanAdapter.CustomViewHolder>(){

    private var onItemClickListener:OnRecyclerViewItemClickListener2?=null

    inner class CustomViewHolder(private val view:View):RecyclerView.ViewHolder(view){
        val nama:TextView = itemView.findViewById(R.id.tvLayoutListPendidikanNama)
        val tahun:TextView = itemView.findViewById(R.id.tvLayoutListPendidikanTahun)
        val nilai:TextView = itemView.findViewById(R.id.tvLayoutListPendidikanNilai)
        val hapus:ImageView = itemView.findViewById(R.id.imgLayoutListPendidikanHapus)
        val edit:ImageView = itemView.findViewById(R.id.imgLayoutListPendidikanEdit)
        init{
            hapus.setOnClickListener {
                onItemClickListener?.OnClick(it, adapterPosition)
            }
            edit.setOnClickListener {
                onItemClickListener?.OnClick2(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var tempdtstart = listpendidikan[position].date_start
        var dtstart = tempdtstart.substringBeforeLast("T")
        var tempdtend = listpendidikan[position].date_end
        var dtend = tempdtend.substringBeforeLast("T")
        holder.nama.text = listpendidikan[position].name
        holder.tahun.text = "${dtstart} sampai ${dtend}"
        holder.nilai.text = "nilai : ${listpendidikan[position].score}"

        if (Action == 1){
            holder.hapus.setVisibility(View.GONE)
            holder.edit.setVisibility(View.GONE)
        }
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

    fun OnClick2(view:View, position: Int)
}
