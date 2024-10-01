package com.example.project

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class EditNote : AppCompatActivity() {

    lateinit var noteIds: List<String> // List of note IDs
    lateinit var editText: EditText
    lateinit var saveButton: Button
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_note)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Retrieve the note ID and description from the intent
        noteIds = intent.getStringArrayListExtra("noteIds") ?: listOf()
        val noteDescription = intent.getStringExtra("noteDescription") ?: ""

        // Initialize views
        editText = findViewById(R.id.edit_note_text)
        saveButton = findViewById(R.id.save_button)

        // Set the note description to the EditText
        editText.setText(noteDescription)

        saveButton.setOnClickListener {
            val updatedNote = editText.text.toString()
            if (updatedNote.isNotEmpty()) {
                updateMultipleNotesInFirestore(noteIds, updatedNote)
            } else {
                Toast.makeText(this, "Note cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

    }

    /*  private fun updateNoteInFirestore(noteId: String, updatedDescription: String) {
          db.collection("Notes").document(noteId)
              .update("note", updatedDescription)
              .addOnSuccessListener {
                  Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()
                  finish() // Close activity after update
              }
              .addOnFailureListener { e ->
                  Toast.makeText(this, "Failed to update note: ${e.message}", Toast.LENGTH_SHORT)
                      .show()
              }
      }*/

    // Method to update multiple notes in Firestore
    private fun updateMultipleNotesInFirestore(noteIds: List<String>, updatedDescription: String) {
        // Track the successful and failed updates
        var successfulUpdates = 0
        var failedUpdates = 0

        // Loop through each noteId and update the corresponding note
        noteIds.forEach { noteId ->
            db.collection("Notes").document(noteId)
                .update("note", updatedDescription)
                .addOnSuccessListener {
                    successfulUpdates++
                    // If all notes have been processed, show the final status
                    if (successfulUpdates + failedUpdates == noteIds.size) {
                        showFinalStatus(successfulUpdates, failedUpdates)
                    }
                }
                .addOnFailureListener { e ->
                    failedUpdates++
                    // If all notes have been processed, show the final status
                    if (successfulUpdates + failedUpdates == noteIds.size) {
                        showFinalStatus(successfulUpdates, failedUpdates)
                    }
                }
        }
    }

    // Helper method to show the final status after processing all updates
    private fun showFinalStatus(successfulUpdates: Int, failedUpdates: Int) {
        Toast.makeText(
            this,
            "Update complete: $successfulUpdates success, $failedUpdates failed",
            Toast.LENGTH_LONG
        ).show()
        finish() // Close activity after update
    }
}