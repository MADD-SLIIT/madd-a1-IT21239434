package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.project.data_class.User
import com.example.project.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.project.R.id.homeUser


class Home : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var databaseRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    lateinit var getusernamegreet: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

       /* supportFragmentManager.beginTransaction()
            .add(R.id.home_frgmnt, HomeFragment())
            .addToBackStack(null)
            .commit()*/

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_containter) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        setupWithNavController(bottomNavigationView, navController)

    }

}








