package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.DocumentsContract.getDocumentId
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.project.data_class.TopicModel
import com.google.firebase.firestore.FirebaseFirestore

class DetaildedTopic2activity : AppCompatActivity() {

    lateinit var bck_topic: ImageButton
    lateinit var QuizBtn: Button
    var isQuizCompleted = false

    lateinit var tpcHead: TextView
    lateinit var tpcSub: TextView
    lateinit var tpcHours: TextView
    lateinit var tpcDescr: TextView
    lateinit var Imagevw: ImageView

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detailded_topic2activity)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        bck_topic = findViewById(R.id.bck_topic)
        QuizBtn = findViewById(R.id.quiz_btn)

        tpcHead = findViewById(R.id.tpc_head)
        tpcSub = findViewById(R.id.tpc_sub)
        tpcHours = findViewById(R.id.tpc_hours)
        tpcDescr = findViewById(R.id.tpc_descr)
        Imagevw = findViewById(R.id.imageViewf)

        enableEdgeToEdge()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Automatically determine the document ID
        val documentId = getDocumentId()
        fetchTopicData(documentId)



        // Check if the quiz was already completed (e.g., from SharedPreferences)
        isQuizCompleted = getSharedPreferences("QuizAppPrefs", MODE_PRIVATE)
            .getBoolean("quizCompleted", false)

        if (isQuizCompleted) {
            // Disable the button if the quiz is already completed
            QuizBtn.isEnabled = false
        }

        //Direct to Quiz
        QuizBtn.setOnClickListener {
            if (isQuizCompleted) {
                // Start QuizActivity and wait for the result
                val intent = Intent(this, QuizActivity::class.java)
                startActivityForResult(intent, 1)
            }else{
                val intent = Intent(this, QuizActivity::class.java)
                startActivity(intent)
            }
        }

        //Direct to Topic Main Screen
        bck_topic.setOnClickListener {
            onBackPressed()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragmentManager = supportFragmentManager
                if (fragmentManager.backStackEntryCount > 0) {
                    fragmentManager.popBackStack() // Pop the top fragment from the back stack
                } else {
                    finish()  // No fragments left, finish the activity
                }
            }
        })

    }

    private fun getDocumentId(): String {
        // Sample logic to determine document ID
        val selectedTopicIndex = intent.getIntExtra("SELECTED_TOPIC_INDEX", -1)
        return when (selectedTopicIndex) {
            0 -> "Asx692DnwhGIRu0t7JyN"  // Example document ID for topic 1
            1 -> "JfNTq5kLA7zdoSw0Opvw"     // Example document ID for topic 2
            2 -> "yetAnotherDocumentId"   // Example document ID for topic 3
            else -> "JfNTq5kLA7zdoSw0Opvw"   // Fallback document ID
        }

    }

    // Updated fetchTopicData method to accept document ID
    private fun fetchTopicData(documentId: String) {
        val documentRef = db.collection("Topics").document(documentId)  // Assuming you have a 'topics' collection and 'topic2' document
        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val topic = documentSnapshot.toObject(TopicModel::class.java)

                    // Populate TextViews with retrieved data
                    topic?.let {
                        tpcHead.text = it.name
                        tpcSub.text = it.description
                        tpcHours.text = it.hours
                        tpcDescr.text = it.big_description

                        // Load image URL into ImageView
                        Glide.with(this) // Use Glide to load the image
                            .load(it.ImageUrl)
                            .into(Imagevw)
                    }
                } else {
                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error fetching data: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Quiz completed
            isQuizCompleted = data?.getBooleanExtra("quizCompleted", false) ?: false

            if (isQuizCompleted) {
                // Disable the Quiz button
                QuizBtn.isEnabled = false

                // Save the completed state to SharedPreferences
                getSharedPreferences("QuizAppPrefs", MODE_PRIVATE)
                    .edit()
                    .putBoolean("quizCompleted", true)
                    .apply()
            }
        }
    }



    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack() // Pop the top fragment from the back stack
        } else {
            super.onBackPressed() // Default back behavior
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}