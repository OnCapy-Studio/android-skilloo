package com.example.skilloapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skilloapp.R
import com.example.skilloapp.data.CardItem

class CardAdapter(var cardItemList: List<CardItem>) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardItem = cardItemList[position]

        holder.timeTextView.text = cardItem.time
        holder.durationTextView.text = cardItem.duration
        holder.labNameTextView.text = cardItem.labName
        holder.subjectTextView.text = cardItem.subject
        holder.roomTextView.text = cardItem.room
    }

    override fun getItemCount(): Int {
        return cardItemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val durationTextView: TextView = itemView.findViewById(R.id.durationTextView)
        val labNameTextView: TextView = itemView.findViewById(R.id.labNameTextView)
        val subjectTextView: TextView = itemView.findViewById(R.id.subjectTextView)
        val roomTextView: TextView = itemView.findViewById(R.id.roomTextView)
    }
}
