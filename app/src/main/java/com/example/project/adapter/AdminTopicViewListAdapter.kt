package com.example.project.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project.R
import com.example.project.data_class.TopicModel

class AdminTopicViewListAdapter(private val admintopicList: List<TopicModel>) :
    RecyclerView.Adapter<AdminTopicViewListAdapter.TopicViewlistViewHolder>() {

    class TopicViewlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val topicName: TextView = itemView.findViewById(R.id.topicName)
        private val topicDesc: TextView = itemView.findViewById(R.id.topicDesc)
        private val topicHours: TextView = itemView.findViewById(R.id.topicHours)
        private val topicBigDesc: TextView = itemView.findViewById(R.id.topicBigDesc)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        @SuppressLint("SetTextI18n")
        fun bind(topic: TopicModel) {
            topicName.text = "Name: ${topic.name}"
            topicDesc.text = "Description: ${topic.description}"
            topicHours.text = "Hours: ${topic.hours}"
            topicBigDesc.text = "Big Description: ${topic.big_description}"

            Glide.with(itemView.context)
                .load(topic.ImageUrl)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewlistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.admintopicviewlist,
                parent,
                false
            ) // Use the correct layout resource name
        return TopicViewlistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return admintopicList.size // Return the size of the list
    }

    override fun onBindViewHolder(holder: TopicViewlistViewHolder, position: Int) {
        holder.bind(admintopicList[position]) // Bind data to the view holder

    }
}