package com.voicenotes

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.media.MediaBrowserServiceCompat

/**
 * MediaBrowserService that enables Android Auto to discover and display this app.
 * This minimal implementation allows the app to appear in Android Auto's app list.
 */
class VoiceNotesMediaBrowserService : MediaBrowserServiceCompat() {

    companion object {
        private const val TAG = "VoiceNotesMediaBrowser"
        private const val ROOT_ID = "voice_notes_root"
        
        // Known Android Auto package names
        private val ALLOWED_PACKAGES = setOf(
            "com.google.android.projection.gearhead",  // Android Auto
            "com.google.android.apps.automotive.inputmethod",  // Android Automotive OS
            "com.google.android.carassistant"  // Google Assistant for Android Auto
        )
    }

    private lateinit var mediaSession: MediaSessionCompat

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: Initializing MediaBrowserService")
        
        // Create MediaSession for Android Auto compatibility
        mediaSession = MediaSessionCompat(this, TAG).apply {
            // Set flags for media button and transport controls
            setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                    MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
            
            // Set callback for playback controls
            setCallback(object : MediaSessionCompat.Callback() {
                override fun onPlay() {
                    Log.d(TAG, "MediaSession.Callback: onPlay")
                    // Handle play action - for voice notes app, this could trigger recording
                }
                
                override fun onPause() {
                    Log.d(TAG, "MediaSession.Callback: onPause")
                    // Handle pause action
                }
                
                override fun onStop() {
                    Log.d(TAG, "MediaSession.Callback: onStop")
                    // Handle stop action
                }
            })
            
            // Activate the session
            isActive = true
        }
        
        // Set the session token - required for Android Auto
        sessionToken = mediaSession.sessionToken
        Log.d(TAG, "onCreate: MediaSession created and activated")
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: Releasing MediaSession")
        // Check if mediaSession was initialized before releasing
        if (::mediaSession.isInitialized) {
            mediaSession.release()
        }
        super.onDestroy()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        Log.d(TAG, "onGetRoot: clientPackageName=$clientPackageName, clientUid=$clientUid")
        
        // Allow Android Auto and other trusted automotive clients to connect
        // For debugging, we're more permissive but log all connection attempts
        return if (ALLOWED_PACKAGES.contains(clientPackageName)) {
            Log.d(TAG, "onGetRoot: Allowing known package: $clientPackageName")
            BrowserRoot(ROOT_ID, null)
        } else {
            // For debugging Android Auto issues, allow connections but log them
            Log.w(TAG, "onGetRoot: Unknown package attempting connection: $clientPackageName")
            // TODO: For production, consider returning null for unknown packages to enhance security
            // Still allow connection for debugging - can be restricted later for security
            BrowserRoot(ROOT_ID, null)
        }
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        Log.d(TAG, "onLoadChildren: parentId=$parentId")
        // Return empty list as this app doesn't provide media items
        // It's primarily a voice note-taking app
        result.sendResult(mutableListOf())
    }
}
