package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project.databinding.ActivitySignInBinding
import com.example.project.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignIN : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    // Predefined admin email and password
    private val adminEmail = "admin@gmail.com"
    private val adminPassword = "Admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        //binding activity to root
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()
        //firebase Database Reference
        databaseRef = FirebaseDatabase.getInstance().reference

        //navigate to register screen
        binding.signUpNoAcc.setOnClickListener {
            directToRegister()
        }

        //Handle login
        binding.SignInBtn.setOnClickListener {
            val email = binding.edtSigninEmail.text.toString()
            val password = binding.edtSigninPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to authenticate user and login
    private fun loginUser(email: String, password: String) {

        // Check if the login is for admin
        if (email == adminEmail && password == adminPassword) {
            // Direct to Admin page
            Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show()
            loginToAdminPage()
        } else {
            // Proceed with normal user login
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val uid = firebaseAuth.currentUser?.uid
                        if (uid != null) {
                            fetchUserData(uid)
                        }
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        LoginToHome()
                    } else {
                        Toast.makeText(
                            this,
                            "Login Failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }

    //log to admin page
    private fun loginToAdminPage() {
        val intent = Intent(this, AdminHome::class.java) // Make sure AdminPage activity exists
        startActivity(intent)
        finish()
    }

    // Function to fetch user data from Firebase Realtime Database
    private fun fetchUserData(uid: String) {
        databaseRef.child("Users").child(uid).get().addOnSuccessListener {
            if (it.exists()) {
                //val email = it.child("email").value
                val name = it.child("name").value
               // val password = it.child("password").value

                Toast.makeText(this, "Welcome,$name!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
        }
    }

    // Navigate to Home screen after successful login
    private fun LoginToHome() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

    // Navigate to Register screen
    private fun directToRegister() {
        val intent = Intent(this, SignUP::class.java)
        startActivity(intent)
        finish()
    }

}





