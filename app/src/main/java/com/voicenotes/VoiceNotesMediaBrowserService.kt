package com.voicenotes

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import androidx.media.MediaBrowserServiceCompat

/**
 * MediaBrowserService that enables Android Auto to discover and display this app.
 * This minimal implementation allows the app to appear in Android Auto's app list.
 */
class VoiceNotesMediaBrowserService : MediaBrowserServiceCompat() {

    companion object {
        private const val ROOT_ID = "voice_notes_root"
        
        // Known Android Auto package names
        private val ALLOWED_PACKAGES = setOf(
            "com.google.android.projection.gearhead",  // Android Auto
            "com.google.android.apps.automotive.inputmethod",  // Android Automotive OS
            "com.google.android.carassistant"  // Google Assistant for Android Auto
        )
    }

    override fun onCreate() {
        super.onCreate()
        sessionToken = null
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        // Allow Android Auto and other trusted automotive clients to connect
        // For security, we validate the client package name
        return if (ALLOWED_PACKAGES.contains(clientPackageName)) {
            BrowserRoot(ROOT_ID, null)
        } else {
            // Return null to reject untrusted clients
            null
        }
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        // Return empty list as this app doesn't provide media items
        // It's primarily a voice note-taking app
        result.sendResult(mutableListOf())
    }
}
