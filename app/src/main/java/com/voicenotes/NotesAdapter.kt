package com.voicenotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private val notes: MutableList<Note>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.noteTitleTextView)
        val timestampTextView: TextView = itemView.findViewById(R.id.noteTimestampTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.noteContentTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.timestampTextView.text = "${note.getFormattedDate()} - ${note.getFormattedTimestamp()}"
        holder.contentTextView.text = note.content
    }

    override fun getItemCount(): Int = notes.size

    fun addNote(note: Note) {
        notes.add(0, note)
        notifyItemInserted(0)
    }
}
