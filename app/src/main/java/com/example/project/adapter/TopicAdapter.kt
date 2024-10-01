package com.example.project.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.DetaildedTopic2activity
import com.example.project.R
import com.example.project.data_class.Topic


class TopicAdapter(private val topicList : List<Topic>):
    RecyclerView.Adapter<TopicAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val title : TextView = itemView.findViewById(R.id.tpc_head)
        val desc : TextView = itemView.findViewById(R.id.tpc_sub)
        val hour : TextView = itemView.findViewById(R.id.tpc_hours)
        val navigateButton: Button = itemView.findViewById(R.id.subsc_btn)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topic_card_view, parent,false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = topicList[position]

        holder.title.text = currentItem.title
        holder.desc.text = currentItem.description
        holder.hour.text = currentItem.hours

        holder.navigateButton.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, DetaildedTopic2activity::class.java)
            context.startActivity(intent)
        }

    }

}