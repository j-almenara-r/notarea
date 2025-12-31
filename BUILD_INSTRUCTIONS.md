# Build Instructions for Voice Notes App

## Prerequisites

You need one of the following:
1. **Android Studio** (recommended - easiest method)
2. **Command line tools** with Android SDK installed

## Method 1: Using Android Studio (Recommended)

### Step-by-Step:

1. **Install Android Studio**
   - Download from: https://developer.android.com/studio
   - Install with default settings
   - Launch Android Studio and complete the setup wizard

2. **Clone the Repository**
   ```bash
   git clone https://github.com/j-almenara-r/studious-garbanzo.git
   ```

3. **Open Project in Android Studio**
   - Launch Android Studio
   - Click "Open"
   - Navigate to the `studious-garbanzo` folder
   - Click "OK"
   - Wait for Gradle sync to complete (this may take a few minutes the first time)

4. **Build the APK**
   - Click **Build** menu → **Build Bundle(s) / APK(s)** → **Build APK(s)**
   - Wait for the build to complete (you'll see a notification)
   - Click "locate" in the notification to find the APK

   OR use the toolbar:
   - Click the hammer icon (🔨) to build
   - Then go to `app/build/outputs/apk/debug/app-debug.apk`

5. **Transfer APK to Your Phone**
   - Option A: Connect phone via USB, copy the APK file
   - Option B: Email the APK to yourself
   - Option C: Upload to Google Drive/Dropbox and download on phone

6. **Install on Samsung Galaxy S25**
   - Locate the APK file on your phone
   - Tap to install
   - If prompted, allow installation from this source
   - Grant microphone permission when app asks

## Method 2: Command Line Build

### Prerequisites:
- Java JDK 8 or higher installed
- Android SDK installed (via Android Studio or standalone)
- `ANDROID_HOME` environment variable set

### Commands:

```bash
# Clone repository
git clone https://github.com/j-almenara-r/studious-garbanzo.git
cd studious-garbanzo

# Build debug APK
./gradlew assembleDebug

# APK location
# app/build/outputs/apk/debug/app-debug.apk

# Install directly to connected device (optional)
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Method 3: Using GitHub Actions (if configured)

If GitHub Actions is set up for this repository, you can:
1. Go to the "Actions" tab on GitHub
2. Run the build workflow
3. Download the built APK from the workflow artifacts

## Troubleshooting

### "SDK location not found"
- Make sure Android SDK is installed
- Set ANDROID_HOME environment variable
- Or create `local.properties` file with: `sdk.dir=/path/to/android/sdk`

### "Could not resolve dependencies"
- Ensure you have internet connection
- Gradle needs to download dependencies on first build
- Try running: `./gradlew clean build`

### "Build failed"
- Update Android Studio to latest version
- Ensure you have Android SDK 34 installed
- Check Java version: `java -version` (needs Java 8+)

### "App won't install on phone"
- Enable "Install from Unknown Sources" or "Install Unknown Apps" in Settings
- Make sure you're allowing installation from the file manager/browser you're using
- Check if you have enough storage space

## What Gets Built

The build process creates:
- **app-debug.apk** - Unsigned debug version (for testing/personal use)
  - File size: ~2-5 MB
  - Location: `app/build/outputs/apk/debug/app-debug.apk`

For a production release (not needed for personal use):
- You would need to sign the APK with a keystore
- Use: `./gradlew assembleRelease`

## Security Note

The debug APK is suitable for personal use. It's not signed for distribution on Google Play Store, but that's fine for installing on your own device.

## Estimated Time

- First time setup: 30-60 minutes (downloading Android Studio, SDK, etc.)
- Subsequent builds: 2-5 minutes
- APK transfer and install: 1-2 minutes

**Total time to working app on phone: 35-70 minutes** (most time is setup)

## After Installation

1. Launch "Voice Notes" app
2. Tap "Allow" when asked for microphone permission
3. Tap the big microphone button
4. Speak your note
5. Done! Your note appears with timestamp

Perfect for dictating notes while driving safely!
