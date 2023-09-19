package com.example.skilloapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skilloapp.R
import com.example.skilloapp.data.model.suporte.SupportRequest

class SupportRequestAdapter(private val supportRequests: List<SupportRequest>) :
    RecyclerView.Adapter<SupportRequestAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
//        val dateTextView: TextView = itemView.findViewById(R.id.textViewDate)
//        val timeTextView: TextView = itemView.findViewById(R.id.textViewTime)
        val locationTextView: TextView = itemView.findViewById(R.id.textViewLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_support, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val supportRequest = supportRequests[position]

        holder.titleTextView.text = supportRequest.titulo
//        holder.dateTextView.text = supportRequest.lab
//        holder.timeTextView.text = supportRequest.descricao
        holder.locationTextView.text = supportRequest.lab
    }

    override fun getItemCount(): Int {
        return supportRequests.size
    }
}
