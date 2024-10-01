package com.example.project.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.adapter.TopicAdapter.ItemViewHolder
import com.example.project.data_class.Note

class NoteAdapter(
    private val noteList: MutableList<Note>,
    private val onEditClicked: (Note) -> Unit,
    private val onDeleteClicked: (Note) -> Unit

) :
    RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {

    // ViewHolder class for holding note items
    class ItemViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val noteDescription: TextView = itemView.findViewById(R.id.note_description)
        val editButton: ImageButton = itemView.findViewById(R.id.edit_note_btn)
        val deleteButton: ImageButton = itemView.findViewById(R.id.dlt_note_btn)

        fun bind(note: Note) {
            noteDescription.text = note.description

        }
    }

    // Create ViewHolder and inflate the note card layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_card, parent, false)
        return ItemViewHolder(view)
    }

    // Bind note data to the ViewHolder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentNote = noteList[position]
        holder.noteDescription.text = currentNote.description

        holder.bind(currentNote)

        holder.editButton.setOnClickListener {
            onEditClicked(currentNote)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClicked(currentNote)
        }

    }

    override fun getItemCount(): Int {
        return noteList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateNotes(nwnoteList: List<Note>) {
        noteList.clear()
        noteList.addAll(nwnoteList)
        notifyDataSetChanged()
    }
}