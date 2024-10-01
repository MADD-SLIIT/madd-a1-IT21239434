package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class spashScreen02 : AppCompatActivity() {

    lateinit var sp_2_nxt_btn : ImageButton
    lateinit var sp_2_bck_btn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spash_screen02)

        sp_2_nxt_btn = findViewById(R.id.sp_2_nxt_btn)
       sp_2_bck_btn = findViewById(R.id.sp_2_bck_btn)

        sp_2_nxt_btn.setOnClickListener(){
            sp2_nxtbtn()
        }


        sp_2_bck_btn.setOnClickListener(){
            sp2_bckbtn()
        }

    }

    fun sp2_nxtbtn(){
        val intent = Intent(this,SelectScreen::class.java)
        startActivity(intent)
        finish()
    }


    fun sp2_bckbtn(){
       val intent = Intent(this,spashScreen01::class.java)
        startActivity(intent)
        finish()
    }

}