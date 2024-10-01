package com.example.project.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.project.FlaggedQuestions
import com.example.project.Myprofile
import com.example.project.R
import com.example.project.SignIN
import com.example.project.SignUP

class MenuFragment : Fragment() {

    lateinit var profile_btn : Button
    lateinit var flagged_questions : Button
    lateinit var logout_btn : Button


    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_menu, container, false)

        // Initialize sharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE)

        profile_btn = view.findViewById(R.id.myprofile)
        flagged_questions = view.findViewById(R.id.quesBookmark)
        logout_btn = view.findViewById(R.id.logout)

        profile_btn.setOnClickListener{
           profile_nav()
        }

        flagged_questions.setOnClickListener {
            nav_flaggedQuiz()
        }


        logout_btn.setOnClickListener{
            LogOut()
        }

        return view
    }

    private fun nav_flaggedQuiz() {
        val intent = Intent(activity, FlaggedQuestions::class.java)
        startActivity(intent)
    }

    private fun profile_nav(){
        val intent = Intent(activity, Myprofile::class.java)
        startActivity(intent)
    }


    private fun LogOut(){
        // Clear the SharedPreferences (or user session data)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Redirect to SignIn activity
        val intent = Intent(activity, SignIN::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }

}