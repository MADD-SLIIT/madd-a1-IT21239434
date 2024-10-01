package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class spashScreen01 : AppCompatActivity() {

    lateinit var sp_1_nxt_btn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spash_screen01)

        sp_1_nxt_btn = findViewById(R.id.sp_1_nxt_btn)

        sp_1_nxt_btn.setOnClickListener(){
            sp_1_nxt()
        }

    }

    fun sp_1_nxt(){
        val intent = Intent(this,spashScreen02::class.java)
        startActivity(intent)
    }
}