package com.example.project

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.adapter.AdminTopicViewListAdapter
import com.example.project.adapter.TopicAdapter
import com.example.project.data_class.TopicModel
import com.google.firebase.firestore.FirebaseFirestore

class AdminTopicViewList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var topicAdapter: AdminTopicViewListAdapter
    private val admintopicList = mutableListOf<TopicModel>()  // List to store the topics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_topic_view_list)

        // Initialize Firestore and RecyclerView
        firestore = FirebaseFirestore.getInstance()
        recyclerView = findViewById(R.id.recyclerViewTopics)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter and attach it to RecyclerView
        topicAdapter = AdminTopicViewListAdapter(admintopicList)
        recyclerView.adapter = topicAdapter

        // Fetch topics from Firestore
        fetchTopicsFromFirestore()
    }

    private fun fetchTopicsFromFirestore() {
        firestore.collection("Topics")
            .get()
            .addOnSuccessListener { result ->
                admintopicList.clear()  // Clear the list before adding new data
                for (document in result) {
                    val admintopic = document.toObject(TopicModel::class.java)
                    admintopic.id = document.id  // Set the document ID
                    admintopicList.add(admintopic)
                }
                topicAdapter.notifyDataSetChanged()  // Notify adapter about data change
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error fetching topics: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}