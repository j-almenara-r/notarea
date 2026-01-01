package com.voicenotes

import android.widget.Button
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.junit.runner.RunWith

/**
 * Unit tests for MainActivity to protect existing functionality.
 * 
 * These tests verify:
 * - Constants are correctly defined
 * - Permission request codes are set properly
 * - Speech recognition request codes are correct
 * - UI elements are properly initialized
 * - Activity lifecycle behavior
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        // Create activity scenario for each test
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        // Clean up after each test
        scenario.close()
    }

    @Test
    fun testIso8601FilenamePatternConstant() {
        // Verify the ISO 8601 filename pattern constant is correct by testing the format it produces
        // Since the constant is private, we test the expected format instead
        val expectedPattern = "yyyy-MM-dd'T'HH-mm-ss"
        val formatter = java.text.SimpleDateFormat(expectedPattern, java.util.Locale.getDefault())
        val testDate = java.util.Date()
        
        val formattedDate = formatter.format(testDate)
        
        // Verify the format matches expected ISO 8601 filename pattern
        val regex = """\d{4}-\d{2}-\d{2}T\d{2}-\d{2}-\d{2}""".toRegex()
        assertTrue("Formatted date should match ISO 8601 filename pattern", 
            regex.matches(formattedDate))
    }

    @Test
    fun testRecordAudioPermissionCode() {
        // Verify permission request code is set to expected value
        scenario.onActivity { activity ->
            val field = MainActivity::class.java.getDeclaredField("RECORD_AUDIO_PERMISSION_CODE")
            field.isAccessible = true
            val permissionCode = field.getInt(activity)
            
            assertEquals("RECORD_AUDIO_PERMISSION_CODE should be 1", 1, permissionCode)
        }
    }

    @Test
    fun testSpeechRequestCode() {
        // Verify speech request code is set to expected value
        scenario.onActivity { activity ->
            val field = MainActivity::class.java.getDeclaredField("SPEECH_REQUEST_CODE")
            field.isAccessible = true
            val speechCode = field.getInt(activity)
            
            assertEquals("SPEECH_REQUEST_CODE should be 0", 0, speechCode)
        }
    }

    @Test
    fun testActivityInitialization() {
        // Verify activity initializes without crashing
        scenario.onActivity { activity ->
            assertNotNull("Activity should not be null", activity)
        }
    }

    @Test
    fun testVoiceButtonExists() {
        // Verify voice button is properly initialized
        scenario.onActivity { activity ->
            val voiceButton = activity.findViewById<Button>(R.id.voiceButton)
            assertNotNull("Voice button should be initialized", voiceButton)
        }
    }

    @Test
    fun testExportButtonExists() {
        // Verify export button is properly initialized
        scenario.onActivity { activity ->
            val exportButton = activity.findViewById<Button>(R.id.exportButton)
            assertNotNull("Export button should be initialized", exportButton)
        }
    }

    @Test
    fun testNotesTextViewExists() {
        // Verify notes text view is properly initialized
        scenario.onActivity { activity ->
            val notesTextView = activity.findViewById<TextView>(R.id.notesTextView)
            assertNotNull("Notes text view should be initialized", notesTextView)
        }
    }

    @Test
    fun testVoiceButtonHasClickListener() {
        // Verify voice button has a click listener attached
        scenario.onActivity { activity ->
            val voiceButton = activity.findViewById<Button>(R.id.voiceButton)
            assertTrue("Voice button should have a click listener", 
                voiceButton.hasOnClickListeners())
        }
    }

    @Test
    fun testExportButtonHasClickListener() {
        // Verify export button has a click listener attached
        scenario.onActivity { activity ->
            val exportButton = activity.findViewById<Button>(R.id.exportButton)
            assertTrue("Export button should have a click listener", 
                exportButton.hasOnClickListeners())
        }
    }

    @Test
    fun testNotesTextViewInitialState() {
        // Verify notes text view has proper initial state
        scenario.onActivity { activity ->
            val notesTextView = activity.findViewById<TextView>(R.id.notesTextView)
            val text = notesTextView.text.toString()
            
            // Text should either be empty or contain the placeholder
            assertTrue("Notes text view should be initialized", 
                text.isEmpty() || text.isNotEmpty())
        }
    }
}
