package com.example.project

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AdminHome : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    lateinit var sharedPreferences: SharedPreferences

    lateinit var addTopicbtn: Button
    lateinit var viewTopicbtn: Button
    lateinit var logoutTopicbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_home)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        addTopicbtn = findViewById(R.id.addTopicbtn)
        viewTopicbtn = findViewById(R.id.vwTopicbtn)
        logoutTopicbtn = findViewById(R.id.logoutadmin)

        addTopicbtn.setOnClickListener {
            val intent = Intent(this, AdminTopicAdd::class.java)
            startActivity(intent)
        }

        viewTopicbtn.setOnClickListener {
            val intent = Intent(this, AdminTopicViewList::class.java)
            startActivity(intent)
        }

        logoutTopicbtn.setOnClickListener {
            val intent = Intent(this, SignIN::class.java)
            startActivity(intent)
            // Clear the SharedPreferences (or user session data)
            /* val editor = sharedPreferences.edit()
             editor.clear()
             editor.apply()

             // Redirect to SignIn activity
             val intent = Intent(this, SignIN::class.java)
             intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
             startActivity(intent)*/
        }


    }
}