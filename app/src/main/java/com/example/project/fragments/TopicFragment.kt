package com.example.project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.data_class.Topic
import com.example.project.adapter.TopicAdapter


class TopicFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_topic, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.topic_list_recyclerView)

        val topiclist = listOf(
            Topic("Title 1", "Description 1", "4", true),
            Topic("Title 2", "Description 2", "5", false),
            Topic("Title 3", "Description 3", "6", false),
            Topic("Title 4", "Description 4", "7", false),
            Topic("Title 5", "Description 5", "8", false),
        )

        // Setting up RecyclerView with LinearLayoutManager and Adapter
        val adapter = TopicAdapter(topiclist)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter // Use your custom adapter


        return view
    }


}