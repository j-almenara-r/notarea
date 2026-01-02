# Voice Notes - Android Auto Compatible Voice Dictation App

An Android app designed for dictating notes while driving, with a simple and safe interface.

## Features

- 🎤 **Voice Recognition**: Uses Android's built-in speech recognition
- 📝 **Timestamped Notes**: Each note is automatically timestamped
- 🚗 **Driver-Friendly**: Large button, minimal interaction required
- 🔒 **Privacy**: All processing happens on-device, no cloud required

## Requirements

- Android device running API 23 (Android 6.0) or higher
- Microphone permission
- Google speech recognition services installed (pre-installed on most devices)

## Building the APK

### Prerequisites

1. Install Android Studio (recommended) or Android SDK command-line tools
2. Java JDK 8 or higher

### Using Android Studio (Easiest)

1. Clone this repository:
   ```bash
   git clone https://github.com/j-almenara-r/studious-garbanzo.git
   cd studious-garbanzo
   ```

2. Open Android Studio and select "Open an Existing Project"

3. Navigate to the cloned repository folder and open it

4. Wait for Gradle sync to complete

5. Connect your Android device via USB (with USB debugging enabled) or start an emulator

6. Click the "Run" button (green play icon) or press Shift+F10

### Using Command Line

1. Clone and navigate to the repository:
   ```bash
   git clone https://github.com/j-almenara-r/studious-garbanzo.git
   cd studious-garbanzo
   ```

2. Build the debug APK:
   ```bash
   ./gradlew assembleDebug
   ```

3. The APK will be generated at:
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

4. Install on your device:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

## Installing on Your Phone

### Quick Install (Recommended for Development)

The fastest way to install during development:
```bash
./gradlew installDebug
```
This builds and installs directly to your connected device in one command!

### Option 1: Direct Install via USB

1. Enable Developer Options and USB Debugging on your Android device
2. Connect your phone to your computer via USB
3. Run: `adb install app/build/outputs/apk/debug/app-debug.apk`

### Option 2: Transfer APK File

1. Copy the APK file to your phone (via USB, email, cloud storage, etc.)
2. On your phone, locate the APK file using a file manager
3. Tap the APK file to install
4. You may need to enable "Install from Unknown Sources" in your phone's settings

### More Distribution Options

For information about distributing your app via Firebase, Google Play Store, F-Droid, and other channels, see **[APP_DISTRIBUTION.md](APP_DISTRIBUTION.md)**.

## Usage

1. **Launch the app** - Tap the Voice Notes icon
2. **Grant microphone permission** - Required for voice recognition (one-time)
3. **Tap the microphone button** - Start speaking your note
4. **Speak naturally** - The app will transcribe your speech
5. **Notes appear with timestamps** - Each note is saved with the time it was created

## Permissions

- **RECORD_AUDIO**: Required to capture voice input for transcription
- **INTERNET**: Used by Google's speech recognition service (if available)

## Architecture

- **Language**: Kotlin
- **Minimum SDK**: API 23 (Android 6.0 Marshmallow)
- **Target SDK**: API 34 (Android 14)
- **Speech Recognition**: Android's built-in `RecognizerIntent` API

## Safety Note

This app is designed to be used with Android Auto or in a hands-free manner. Always prioritize safety while driving:
- Use voice commands only
- Pull over if you need to interact with the screen
- Consider using with Android Auto for the best hands-free experience

## Future Enhancements (Not in MVP)

- Save notes to file/database
- Share notes via other apps
- Clear notes button
- Enhanced Android Auto integration
- Voice commands for note management

## Troubleshooting

**"Speech recognition not available"**: Your device may not have Google speech services installed. Install "Google" or "Google Play Services" from the Play Store.

**Microphone permission denied**: Go to Settings > Apps > Voice Notes > Permissions and enable microphone access.

**App won't install**: Make sure you have "Install from Unknown Sources" enabled for the app you're using to install (e.g., File Manager).

## License

See LICENSE file for details.
