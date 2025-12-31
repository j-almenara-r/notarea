package com.voicenotes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
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
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var voiceButton: Button
    private lateinit var notesTextView: TextView
    private val RECORD_AUDIO_PERMISSION_CODE = 1
    private val SPEECH_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        voiceButton = findViewById(R.id.voiceButton)
        notesTextView = findViewById(R.id.notesTextView)

        voiceButton.setOnClickListener {
            checkPermissionAndStartSpeechRecognition()
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
                val newText = if (currentText.isEmpty()) {
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
}
