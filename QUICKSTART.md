# Quick Start Guide

## For the User Who Needs the APK NOW

### Fastest Way to Get the App Running:

1. **On a computer with Android Studio**:
   ```bash
   git clone https://github.com/j-almenara-r/studious-garbanzo.git
   cd studious-garbanzo
   ./gradlew assembleDebug
   ```

2. **The APK will be at**: `app/build/outputs/apk/debug/app-debug.apk`

3. **Transfer to your Samsung Galaxy S25**:
   - Connect phone via USB and copy the APK file
   - OR email it to yourself
   - OR upload to Google Drive and download on phone

4. **Install on phone**:
   - Open the APK file on your phone
   - Tap "Install" (you may need to allow installation from this source)
   - Done!

5. **Use it**:
   - Open "Voice Notes" app
   - Grant microphone permission when asked
   - Tap the big microphone button
   - Speak your note
   - Done! It appears with a timestamp

## No Computer? Alternative Options:

### Option A: Use GitHub Actions (if enabled)
- The repository can be set up with GitHub Actions to automatically build APKs
- Check the "Actions" tab or "Releases" section on GitHub

### Option B: Use Online Build Services
- Services like Appetize.io or similar can build from the repository
- This requires uploading the repository code

### Option C: Ask Someone with Android Studio
- Share this repository link with them
- They can build and send you the APK

## What Makes This App Special for Driving:

✓ **One Big Button**: Tap and speak, that's it
✓ **No typing needed**: 100% voice-based
✓ **Timestamps**: Know when you said what
✓ **Works offline**: Uses on-device speech recognition
✓ **Simple**: No login, no setup, just works

## Tips for Best Experience:

- Speak clearly and at a normal pace
- Wait for the microphone prompt before speaking
- Keep sentences reasonably short
- Check notes when safely parked

## Known Limitations (MVP - Minimal Viable Product):

- Notes are temporary (cleared when app closes)
- No note editing or deletion yet
- Basic UI (functional, not fancy)
- No cloud sync

These can be added in future versions if needed!
