package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteAccount : AppCompatActivity() {

    lateinit var BckToProfilebtn: ImageButton
    lateinit var Deletebtn: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delete_account)

        BckToProfilebtn = findViewById(R.id.delt_To_prof)
        Deletebtn = findViewById(R.id.delete_account_btn)

        // Initialize Firebase Auth and Database Reference
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference()

        BckToProfilebtn.setOnClickListener {
            backtoProfile()
        }

        Deletebtn.setOnClickListener {
            deleteAccount()
        }

    }

    private fun backtoProfile() {
        val intent = Intent(this, Myprofile::class.java)
        startActivity(intent)
    }

    //delete account
    private fun deleteAccount() {
        databaseRef.child("Users").child(auth.currentUser?.uid.toString()).removeValue()
        val intent = Intent(this, SignUP::class.java)
        startActivity(intent)
    }
}