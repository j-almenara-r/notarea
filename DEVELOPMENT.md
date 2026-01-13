# Development Quick Reference

Quick commands for common development tasks.

## Building & Installing

### Fastest Way (Recommended)
Build and install to connected device in one command:
```bash
./gradlew installDebug
```

### Just Build
```bash
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

### Build Release Version
```bash
./gradlew assembleRelease
```
Note: Requires signing configuration for release builds.

## Installing APK

### Via Gradle (Easiest)
```bash
./gradlew installDebug
```

### Via ADB
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Wireless Install
First-time setup (phone connected via USB):
```bash
adb tcpip 5555
adb connect YOUR_PHONE_IP:5555
```

Then install wirelessly:
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Debugging

### View Logs
```bash
adb logcat | grep "VoiceNotes"
```

### Clear App Data
```bash
adb shell pm clear com.voicenotes
```

### Uninstall App
```bash
adb uninstall com.voicenotes
```

## Testing

### Run Unit Tests
```bash
./gradlew test
```

### Run Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

## Cleaning

### Clean Build
```bash
./gradlew clean
```

### Clean and Rebuild
```bash
./gradlew clean assembleDebug
```

## Version Management

Version information is in `app/build.gradle`:
- `versionCode`: Increment for each release (integer)
- `versionName`: Human-readable version (e.g., "1.0", "1.1")

## Distribution

For detailed information about distributing your app, see [APP_DISTRIBUTION.md](APP_DISTRIBUTION.md).

### Quick Options:
1. **Personal testing:** `./gradlew installDebug` (fastest!)
2. **Share with testers:** Firebase App Distribution
3. **Public testing:** Google Play Store (internal/beta track)
4. **Production:** Google Play Store
