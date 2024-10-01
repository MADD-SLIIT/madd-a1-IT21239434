package com.example.project

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project.data_class.Note
import com.google.firebase.firestore.FirebaseFirestore

class AddNotes : AppCompatActivity() {

    private lateinit var NotedoneBtn: Button
    private lateinit var noteInput: EditText
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_notes)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        noteInput = findViewById(R.id.NoteInput)
        NotedoneBtn = findViewById(R.id.note_added_btn)

        NotedoneBtn.setOnClickListener {
            val noteText = noteInput.text.toString().trim()
            if (TextUtils.isEmpty(noteText)) {
                Toast.makeText(this, "Note cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                // Call function to add note to Firestore
                addNoteToFirestore(noteText)
            }
        }

    }

    private fun addNoteToFirestore(Note: String) {

        // Create a new note object
        val noteMap = hashMapOf(
            "note" to Note
        )

        // Add a new document to the "Notes" collection
        db.collection("Notes")
            .add(noteMap)
            .addOnSuccessListener {
                // Success feedback to the user
                Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show()

                // Optionally clear the input field after success
                noteInput.text.clear()

                // Optionally finish activity to go back to the previous screen
                finish()
            }
            .addOnFailureListener {
                // Failure feedback to the user
                Toast.makeText(this, "Error adding note", Toast.LENGTH_SHORT).show()
            }
    }

}