package com.voicenotes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var fabNewNote: FloatingActionButton
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var emptyStateTextView: TextView
    private lateinit var notesAdapter: NotesAdapter
    private val notes = mutableListOf<Note>()
    
    private var currentNoteTitle: String? = null
    
    private val RECORD_AUDIO_PERMISSION_CODE = 1
    private val SPEECH_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabNewNote = findViewById(R.id.fabNewNote)
        notesRecyclerView = findViewById(R.id.notesRecyclerView)
        emptyStateTextView = findViewById(R.id.emptyStateTextView)

        notesAdapter = NotesAdapter(notes)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        notesRecyclerView.adapter = notesAdapter

        fabNewNote.setOnClickListener {
            showNewNoteDialog()
        }
        
        updateEmptyState()
    }

    private fun showNewNoteDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_note_title, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.noteTitleEditText)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton(R.string.create_button) { _, _ ->
                val title = titleEditText.text.toString().trim()
                currentNoteTitle = if (title.isEmpty()) {
                    getString(R.string.default_note_title)
                } else {
                    title
                }
                checkPermissionAndStartSpeechRecognition()
            }
            .setNegativeButton(R.string.cancel_button, null)
            .show()
    }

    private fun updateEmptyState() {
        if (notes.isEmpty()) {
            emptyStateTextView.visibility = View.VISIBLE
            notesRecyclerView.visibility = View.GONE
        } else {
            emptyStateTextView.visibility = View.GONE
            notesRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun checkPermissionAndStartSpeechRecognition() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_PERMISSION_CODE
            )
        } else {
            startSpeechRecognition()
        }
    }

    private fun startSpeechRecognition() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(
                this,
                "Speech recognition not available on this device",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your note...")
        }

        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Error starting speech recognition: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = results?.get(0) ?: ""

            val noteTitle = currentNoteTitle
            if (spokenText.isNotEmpty() && noteTitle != null) {
                val note = Note(
                    title = noteTitle,
                    content = spokenText
                )
                notesAdapter.addNote(note)
                updateEmptyState()
                currentNoteTitle = null
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == RECORD_AUDIO_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSpeechRecognition()
            } else {
                Toast.makeText(
                    this,
                    "Microphone permission is required for voice notes",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
