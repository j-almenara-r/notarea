# App Distribution Guide

This guide explains various channels for distributing your Android app, from development builds to production releases.

## Current Setup: GitHub Releases

You're currently using **GitHub Releases** (build-13), which is a simple but manual solution:
- ✅ **Pros**: Simple, version controlled, no additional accounts needed
- ❌ **Cons**: Requires manual upload to OneDrive, then download to phone

## Distribution Channels for Unstable/Development Builds

### 1. Firebase App Distribution (Recommended for Development)

**What is it?** Firebase App Distribution is Google's official solution for distributing pre-release versions of your app to testers.

**Why use it?**
- ✅ Quick setup and integration with GitHub Actions
- ✅ Testers get instant notifications when new builds are available
- ✅ Install directly via Firebase app or download link
- ✅ Track which testers have which versions
- ✅ Add release notes for each build
- ✅ Free for small teams
- ✅ Integrates with Google Play Console for alpha/beta testing

**Typical Use Case:** Internal testing, QA team, beta testers (small to medium group)

**How to set it up:**
1. Create a Firebase project at https://console.firebase.google.com
2. Add your Android app to the project
3. Generate a service account key for CI/CD integration
4. Update GitHub Actions workflow to upload APK to Firebase
5. Invite testers via email - they'll receive links to install

**📖 See detailed setup instructions in [FIREBASE_SETUP.md](FIREBASE_SETUP.md)**

**Installation on phone:**
- Testers install the Firebase App Distribution app
- They receive notifications when new builds are available
- One-tap install from the app

### 2. Direct ADB Install (Fastest for Personal Development)

**What is it?** Installing APKs directly via USB using Android Debug Bridge.

**Why use it?**
- ✅ Fastest method for personal development
- ✅ No intermediate services needed
- ✅ Works offline
- ❌ Requires USB cable and computer nearby

**How to set it up:**
1. Enable Developer Options on your phone
2. Enable USB Debugging
3. Connect phone via USB
4. Run: `adb install app/build/outputs/apk/debug/app-debug.apk`

**Or even faster with Gradle:**
```bash
./gradlew installDebug
```

This builds and installs in one command!

### 3. F-Droid (Open Source Distribution)

**What is it?** F-Droid is an alternative app store focused on free and open-source software (FOSS).

**Why use it?**
- ✅ Reach FOSS enthusiasts
- ✅ No Google dependencies required
- ✅ Builds are reproducible and transparent
- ❌ Long review process (weeks)
- ❌ Strict FOSS requirements (no proprietary dependencies)
- ❌ You don't control the builds (F-Droid builds from source)

**Typical Use Case:** Stable releases of FOSS apps for privacy-conscious users

**Important Note:** F-Droid is **NOT** for unstable/development builds. It's for stable, open-source releases.

**Is it related to Firebase?** No, they serve completely different purposes:
- **Firebase App Distribution**: For distributing test builds to specific testers
- **F-Droid**: Public app store for stable FOSS apps

### 4. Self-Hosted Solutions

**Options:**
- Host APKs on your own web server with a simple download page
- Use GitHub Actions artifacts (available for 90 days)
- Create a QR code that links directly to the APK download

**Pros:** Full control, no third-party dependencies
**Cons:** Less convenient than dedicated distribution platforms

## Production Distribution

### Google Play Store (Official Distribution Channel)

**What is it?** The official app store for Android, reaching billions of users.

**Distribution Tracks:**
1. **Internal Testing** - Up to 100 testers, no review needed, instant publishing
2. **Closed Testing (Alpha/Beta)** - Limited audience, faster review than production
3. **Open Testing** - Anyone can join, good for public betas
4. **Production** - Public release to everyone

**Publishing Process:**

#### Initial Setup (One-time):
1. Create a Google Play Developer account ($25 one-time fee)
2. Complete the store listing (app name, description, screenshots, icon)
3. Set up content rating and privacy policy
4. Configure app pricing (free or paid)

#### Publishing Your First Release:
1. Build a **signed release APK** or **Android App Bundle (AAB)**
   ```bash
   ./gradlew bundleRelease
   ```
2. Create a signing key (keep this secure!)
3. Upload the AAB to Play Console
4. Fill in release notes
5. Submit for review (usually 1-3 days for first release)

#### Subsequent Updates:
1. Increment version code and version name in `app/build.gradle`
2. Build new release
3. Upload to desired track (internal/alpha/beta/production)
4. Reviews are faster for updates (hours to 1 day)

**Recommended Path to Production:**
1. Start with **Internal Testing** - share with close friends/colleagues
2. Move to **Closed Beta** - expand to more testers
3. Optionally do **Open Beta** - public testing
4. Finally publish to **Production**

**Key Requirements:**
- Signed release build (not debug)
- Privacy policy URL (if you collect any user data)
- Content rating questionnaire
- Target latest API levels (Google enforces this)

## Recommended Workflow for Your Use Case

Based on your needs, here's the recommended approach:

### For Daily Development (Fastest):
```bash
# Build and install directly to connected phone
./gradlew installDebug
```
This takes seconds and doesn't require any uploads!

### For Testing Builds (Friends/Testers):
**Option A: Firebase App Distribution** (Recommended)
- Set up Firebase integration in GitHub Actions
- Automatic distribution on every push
- Testers get notifications

**Option B: GitHub Releases + QR Code**
- Keep current workflow
- Add QR code generation to release notes
- Testers scan QR code to download

### For Stable Releases:
**Google Play Store - Internal Testing Track**
- Publish to Play Store's internal testing
- Share link with testers
- One-click install from Play Store (much easier than APK files!)

### For Public Release:
**Google Play Store - Production**
- Full public release on Play Store
- Automatic updates for users
- Analytics and crash reporting

## Quick Wins to Improve Your Current Setup

### 1. Add Firebase App Distribution to GitHub Actions
Update `.github/workflows/build-apk.yml` to upload to Firebase after building.

### 2. Enable Wireless ADB
You can install via WiFi without cables:
```bash
adb tcpip 5555
adb connect YOUR_PHONE_IP:5555
adb install app-debug.apk
```

### 3. Use Play Store Internal Testing
Even if you don't publish publicly, internal testing gives you:
- Easy distribution link
- Install from Play Store (no "unknown sources" warnings)
- Automatic updates

### 4. Generate Release Notes Automatically
Include commit messages in release notes so you know what changed.

## Summary: Firebase vs F-Droid

**They are NOT related and serve different purposes:**

| Feature | Firebase App Distribution | F-Droid |
|---------|---------------------------|---------|
| **Purpose** | Pre-release testing | Stable public releases |
| **Speed** | Instant | Weeks (review + build) |
| **Control** | You control builds | F-Droid builds from source |
| **Audience** | Selected testers | Public FOSS users |
| **Cost** | Free | Free |
| **Best for** | Development/Beta | Open source final releases |

**For your use case (getting builds to your phone quickly), Firebase App Distribution is the answer, not F-Droid.**

## Next Steps

1. **Immediate improvement:** Use `./gradlew installDebug` for fastest personal testing
2. **Short term:** Set up Firebase App Distribution for easier sharing with others
3. **Long term:** Publish to Google Play Store internal testing track for even easier distribution
4. **Public release:** Submit to Play Store production when ready for public users

## Resources

- [Firebase App Distribution](https://firebase.google.com/docs/app-distribution)
- [Google Play Console](https://play.google.com/console)
- [F-Droid Submission](https://f-droid.org/docs/Submitting_to_F-Droid/)
- [Android App Bundles](https://developer.android.com/guide/app-bundle)
- [Signing Your App](https://developer.android.com/studio/publish/app-signing)
