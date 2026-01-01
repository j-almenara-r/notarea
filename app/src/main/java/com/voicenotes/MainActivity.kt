package com.voicenotes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private val CREATE_FILE_REQUEST_CODE = 2
    
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
        } else if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                writeNotesToUri(uri)
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

        // Generate ISO 8601 timestamp for filename (filename-safe format)
        val filenameTimestamp = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(ISO_8601_FILENAME_PATTERN))
        } else {
            SimpleDateFormat(ISO_8601_FILENAME_PATTERN, Locale.getDefault()).format(Date())
        }
        
        // Use Storage Access Framework to let user choose location
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/markdown"
            putExtra(Intent.EXTRA_TITLE, "voice-notes-$filenameTimestamp.md")
        }

        try {
            startActivityForResult(intent, CREATE_FILE_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                getString(R.string.export_error, e.message),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun writeNotesToUri(uri: Uri) {
        val currentText = notesTextView.text.toString()
        
        try {
            // Generate human-readable timestamp for content
            val readableTimestamp = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            } else {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            }

            // Write content to the file with markdown formatting
            contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write("# Voice Notes Export\n\n".toByteArray())
                outputStream.write("**Exported on:** $readableTimestamp\n\n".toByteArray())
                outputStream.write("---\n\n".toByteArray())
                outputStream.write(currentText.toByteArray())
                outputStream.write("\n".toByteArray())
            }

            Toast.makeText(
                this,
                getString(R.string.export_success_new),
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
