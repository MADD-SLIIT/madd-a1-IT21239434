package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.project.data_class.User
import com.example.project.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUP : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //binding activity to root
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()
        //firebase Database Reference
        databaseRef = FirebaseDatabase.getInstance().reference


        // Set click listener for the Sign Up button
        binding.SignUpBtn.setOnClickListener {
            val name = binding.edtSignupUsername.text.toString()
            val email = binding.edtSignupEmail.text.toString()
            val password = binding.edtSignupPassword.text.toString()
            val confirmpassword = binding.edtSignupConfirmPassword.text.toString()
            val phone = binding.edtSignupPhone.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty() && confirmpassword.isNotEmpty()) {
                signUpUser(name, email, password, confirmpassword, phone)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        //navigate to login screen
        binding.signUpToAcc.setOnClickListener {
            directToLogscreen()
        }

    }

    // Function to sign up the user
    private fun signUpUser(
        name: String,
        email: String,
        password: String,
        confirmpassword: String,
        phone: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid
                    if (uid != null) {
                        addUserToDatabase(name, email, uid, password, phone)
                    }
                    Toast.makeText(this, "SignUp Success", Toast.LENGTH_SHORT).show()
                    directToHomescreen()
                } else {
                    Toast.makeText(
                        this,
                        "Signup Failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    // Function to add user data to Firebase Realtime Database
    private fun addUserToDatabase(
        name: String,
        email: String,
        uid: String,
        password: String,
        phone: String
    ) {
        val user = User(name, email, uid, password, phone)
        databaseRef.child("Users").child(uid).setValue(user)
    }

    //navigation to login Screen
    private fun directToLogscreen() {
        val intent = Intent(this, SignIN::class.java)
        startActivity(intent)
        finish()
    }

    // Navigate to home screen
    private fun directToHomescreen() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }
}





