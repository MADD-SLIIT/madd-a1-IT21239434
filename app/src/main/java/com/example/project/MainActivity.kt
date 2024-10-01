package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var start_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        start_btn = findViewById(R.id.gt_start_btn)

        start_btn.setOnClickListener() {
            app_start()
        }

    }

    private fun app_start() {
        val intent = Intent(this, spashScreen01::class.java)
        startActivity(intent)
    }

}