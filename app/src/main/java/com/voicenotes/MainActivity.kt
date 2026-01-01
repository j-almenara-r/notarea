package com.voicenotes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var voiceButton: Button
    private lateinit var exportButton: Button
    private lateinit var notesTextView: TextView
    private val RECORD_AUDIO_PERMISSION_CODE = 1
    private val SPEECH_REQUEST_CODE = 0
    
    companion object {
        private const val ISO_8601_FILENAME_PATTERN = "yyyy-MM-dd'T'HH-mm-ss"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        voiceButton = findViewById(R.id.voiceButton)
        exportButton = findViewById(R.id.exportButton)
        notesTextView = findViewById(R.id.notesTextView)

        voiceButton.setOnClickListener {
            checkPermissionAndStartSpeechRecognition()
        }

        exportButton.setOnClickListener {
            exportNotesToMarkdown()
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

            if (spokenText.isNotEmpty()) {
                val timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                val currentText = notesTextView.text.toString()
                val placeholderText = getString(R.string.notes_placeholder)
                val newText = if (currentText.isEmpty() || currentText == placeholderText) {
                    "[$timestamp] $spokenText"
                } else {
                    "$currentText\n\n[$timestamp] $spokenText"
                }
                notesTextView.text = newText
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

    private fun exportNotesToMarkdown() {
        val currentText = notesTextView.text.toString()
        val placeholderText = getString(R.string.notes_placeholder)

        // Check if there are notes to export
        if (currentText.isEmpty() || currentText == placeholderText) {
            Toast.makeText(this, getString(R.string.export_no_notes), Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // Generate ISO 8601 timestamp for filename (filename-safe format)
            val filenameTimestamp = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(ISO_8601_FILENAME_PATTERN))
            } else {
                SimpleDateFormat(ISO_8601_FILENAME_PATTERN, Locale.getDefault()).format(Date())
            }
            
            // Generate human-readable timestamp for content
            val readableTimestamp = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            } else {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            }

            // Create VoiceNotes directory in Documents
            val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val voiceNotesDir = File(documentsDir, "VoiceNotes")
            
            if (!voiceNotesDir.exists()) {
                voiceNotesDir.mkdirs()
            }

            // Create the markdown file
            val filename = "voice-notes-$filenameTimestamp.md"
            val file = File(voiceNotesDir, filename)

            // Write content to the file with markdown formatting
            FileWriter(file).use { writer ->
                writer.write("# Voice Notes Export\n\n")
                writer.write("**Exported on:** $readableTimestamp\n\n")
                writer.write("---\n\n")
                writer.write(currentText)
                writer.write("\n")
            }

            Toast.makeText(
                this,
                getString(R.string.export_success, file.absolutePath),
                Toast.LENGTH_LONG
            ).show()

        } catch (e: Exception) {
            Toast.makeText(
                this,
                getString(R.string.export_error, e.message),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
