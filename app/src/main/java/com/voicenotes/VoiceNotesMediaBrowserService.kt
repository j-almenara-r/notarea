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
        private const val EMPTY_ROOT_ID = "empty_root"
    }

    override fun onCreate() {
        super.onCreate()
        sessionToken = null
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        // Allow Android Auto to connect to this service
        return BrowserRoot(ROOT_ID, null)
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
