package com.example.project.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.project.R
import com.example.project.data_class.User
import com.example.project.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var getusernamehead: TextView

    private lateinit var userList: ArrayList<User>

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment using View Binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root


        // Initialize Firebase Auth and Database Reference
        firebaseAuth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference("Users")

        getusernamehead = binding.homeUser
        userList = ArrayList()

        // Get current user UID
        val uid = firebaseAuth.currentUser?.uid
        if (uid != null) {
            fetchUserData(uid) // Fetch user data and update TextView
        }

        return view
    }

    private fun fetchUserData(uid: String) {
        databaseRef.child("Users").child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        // Retrieve user data from snapshot
                        val currentUser = snapshot.getValue(User::class.java)
                        println(currentUser?.name)

                        // Update TextView with the fetched user name
                        getusernamehead.text = currentUser?.name ?: "dfs"

                    } else {
                        //Toast.makeText(this@Home, "User data not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("HomeFragment", "Failed to retrieve data: ${error.message}")
                    /* Toast.makeText(
                         this@HomeFragment,
                         "Failed to retrieve data",
                         Toast.LENGTH_SHORT
                     ).show()*/
                }
            })
    }

    // Clean up binding when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}