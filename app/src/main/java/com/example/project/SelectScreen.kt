package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SelectScreen : AppCompatActivity() {

    lateinit var slct_signIn : Button
    lateinit var slct_signUp : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_screen)

        slct_signIn = findViewById(R.id.slct_sign_in_btn)
        slct_signUp = findViewById(R.id.slct_sign_up_btn)

        slct_signIn.setOnClickListener(){
            select_signIn_mtd()
        }

        slct_signUp.setOnClickListener(){
            select_signUp_mtd()
        }
    }

    private fun select_signUp_mtd(){
        val intent = Intent(this,SignUP::class.java)
        startActivity(intent)
    }

    private fun select_signIn_mtd(){
        val intent = Intent(this,SignIN::class.java)
        startActivity(intent)
    }
}