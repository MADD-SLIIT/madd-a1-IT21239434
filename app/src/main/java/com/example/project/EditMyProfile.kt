package com.example.project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project.data_class.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditMyProfile : AppCompatActivity() {

    lateinit var viewProfileback: ImageButton
    lateinit var edtprofilebtn: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    lateinit var gname: EditText
    lateinit var gphone: EditText
    lateinit var gemail: EditText
    lateinit var getpw: EditText

    private lateinit var userlist: ArrayList<User>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_my_profile)

        viewProfileback = findViewById(R.id.view_profile_bck)
        edtprofilebtn = findViewById(R.id.edt_profile_accept)

        gname = findViewById(R.id.edtusersname)
        gphone = findViewById(R.id.edtphone_no)
        gemail = findViewById(R.id.edtEmail)
        getpw = findViewById(R.id.edtpw)

        // Initialize Firebase Auth and Database Reference
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference()

        userlist = ArrayList()

        retriveUserprofile()

        viewProfileback.setOnClickListener {
            viewProfileback()
        }

        edtprofilebtn.setOnClickListener {
            updateprofile()
        }


    }

    private fun retriveUserprofile() {
        val currentUser = auth.currentUser
        currentUser?.let {
            val uid = it.uid

            // Retrieve data from Firebase Realtime Database
            databaseRef.child("Users").child(uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val names = snapshot.child("name").value.toString()
                        val phoneNO = snapshot.child("phone").value.toString()
                        val emails = snapshot.child("email").value.toString()
                        val pw = snapshot.child("password").value.toString()

                        // Set these values to EditText fields
                        //gname.setText(name)
                        //gphone.setText(phoneNO)
                        //gemail.setText(emails)

                        findViewById<EditText>(R.id.edtusersname).setText(names)
                        findViewById<EditText>(R.id.edtEmail).setText(emails)
                        findViewById<EditText>(R.id.edtphone_no).setText(phoneNO)
                        findViewById<EditText>(R.id.edtpw).setText(pw)

                    }

                    override fun onCancelled(error: DatabaseError) {
                        /* Toast.makeText(
                            this@Myprofile,
                            "Failed to load profile",
                            Toast.LENGTH_SHORT
                        ).show()*/
                    }
                })

        }
    }

    private fun updateprofile() {

        val newName = gname.text.toString()
        val newphone = gphone.text.toString()
        val newemail = gemail.text.toString()
        val newpw = getpw.text.toString()

        databaseRef.child("Users").child(auth.currentUser?.uid.toString())
            .setValue(User(newName, newemail, auth.currentUser?.uid.toString(), newpw, newphone))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@EditMyProfile,
                        "Profile updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, Myprofile::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(
                        this@EditMyProfile,
                        "Failed to update profile",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

    }

    private fun viewProfileback() {
        val intent = Intent(this, Myprofile::class.java)
        startActivity(intent)
        finish()
    }

}