package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.project.data_class.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Myprofile : AppCompatActivity() {

    private lateinit var bckMenu: ImageButton
    private lateinit var editProfileBtn: Button
    private lateinit var deltProfileBtn: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    lateinit var gname: TextView
    lateinit var gphone: TextView
    lateinit var gemail: TextView

    private lateinit var userlist: ArrayList<User>


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_myprofile)

        editProfileBtn = findViewById(R.id.edt_prof)
        bckMenu = findViewById(R.id.menu_To_bck)
        deltProfileBtn = findViewById(R.id.dlt_prof)

        gname = findViewById(R.id.edtusersname)
        gphone = findViewById(R.id.edtphone_no)
        gemail = findViewById(R.id.edtEmail)

        // Initialize Firebase Auth and Database Reference
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference()

        userlist = ArrayList()

        // Get current user UID
        val uid = auth.currentUser?.uid.toString()
        listenForProfileChanges(uid)

        enableEdgeToEdge()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bckMenu.setOnClickListener {
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


    private fun listenForProfileChanges(uid: String) {
        databaseRef.child("Users").child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    // Retrieve user data from snapshot
                    val name = snapshot.getValue(User::class.java)
                    val email = snapshot.getValue(User::class.java)
                    val phone = snapshot.getValue(User::class.java)

                    val user = snapshot.getValue(User::class.java)

                    println(name?.name)
                    println(email?.email)

                    // Set the values to TextViews

                    if (user != null) {
                        gname.text = name?.name
                        gemail.text = email?.email
                        gphone.text = phone?.phone
                    } else {
                        Toast.makeText(this@Myprofile, "User data not found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    /* Toast.makeText(
                         this@Myprofile,
                         "Failed to retrieve data: ${error.message}",
                         Toast.LENGTH_SHORT
                     ).show()*/
                }
            })
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

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.menu_fragment, fragment)
            .addToBackStack(null) // Add to back stack to allow back navigation
            .commit()
    }

    private fun navigateToEditProfile() {
        val intent = Intent(this, EditMyProfile::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigatetoDeleteProfile() {
        val intent = Intent(this, DeleteAccount::class.java)
        startActivity(intent)
        finish()
    }

}


