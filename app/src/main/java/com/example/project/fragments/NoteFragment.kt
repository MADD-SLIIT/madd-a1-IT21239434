package com.example.project.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.AddNotes
import com.example.project.EditNote
import com.example.project.R
import com.example.project.adapter.NoteAdapter
import com.example.project.adapter.TopicAdapter
import com.example.project.data_class.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration


class NoteFragment : Fragment() {

    lateinit var noteaddbtn: ImageButton

    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: NoteAdapter
    private lateinit var notesListener: ListenerRegistration
    private var noteList: MutableList<Note> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_note, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.note_recycle)

        noteaddbtn = view.findViewById(R.id.add_note_btn)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Setup RecyclerView
        //adapter = NoteAdapter(mutableListOf())

        // Setup RecyclerView and adapter
        adapter = NoteAdapter(noteList, { note ->
            // Handle edit click
            directToEditNote(note)
        }, { note ->
            // Handle delete click
            deleteNoteFromFirestore(note.id)
        })


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        // Fetch notes from Firestore
        fetchNotesFromFirestore()

        noteaddbtn.setOnClickListener {
            directToAddNote()
        }

        return view
    }

    private fun deleteNoteFromFirestore(id: String?) {
        if (id != null) {
            db.collection("Notes").document(id)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error deleting note", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun directToEditNote(note: Note) {
        val intent = Intent(activity, EditNote::class.java)
        intent.putStringArrayListExtra(
            "noteIds",
            ArrayList(
                listOf(
                    "YxsnIaZa32IDy87xuJdI",
                )
            )
        )
        intent.putExtra("noteDescription", note.description)
        startActivity(intent)
    }

    // Fetch notes from Firestore and listen for real-time updates
    private fun fetchNotesFromFirestore() {
        notesListener = db.collection("Notes")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                val notes = mutableListOf<Note>()
                if (snapshot != null && !snapshot.isEmpty) {
                    for (document in snapshot.documents) {
                        val note = document.getString("note") ?: ""
                        val od = document.getString("id") ?: ""
                        notes.add(Note(note, od))
                    }
                    // Update adapter with the fetched notes
                    adapter.updateNotes(notes)
                }
            }
    }

    private fun directToAddNote() {
        val intent = Intent(activity, AddNotes::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        notesListener.remove() // Remove listener when fragment is destroyed
    }

}