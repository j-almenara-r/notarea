# Firebase App Distribution Setup

This guide provides a ready-to-use GitHub Actions workflow for automatic distribution to Firebase App Distribution.

## Prerequisites

1. **Firebase Project**: Create a project at https://console.firebase.google.com
2. **Add Android App**: Register your app in Firebase (package name: `com.voicenotes`)
3. **Service Account**: Create a service account for CI/CD

### Creating a Service Account

1. Go to [Google Cloud Console](https://console.cloud.google.com)
2. Select your Firebase project
3. Navigate to "IAM & Admin" > "Service Accounts"
4. Click "Create Service Account"
5. Name it (e.g., "github-actions-firebase")
6. Grant roles:
   - **Firebase App Distribution Admin**
   - **Service Account User**
7. Create a key (JSON format)
8. Download the JSON key file

### Adding Secrets to GitHub

1. Go to your repository settings
2. Navigate to "Secrets and variables" > "Actions"
3. Add new repository secret:
   - Name: `FIREBASE_SERVICE_ACCOUNT`
   - Value: Paste the entire content of the JSON key file

### Firebase App Configuration

1. In Firebase Console, go to App Distribution
2. Click "Get Started"
3. Invite testers by email
4. Testers will receive invitation emails

## GitHub Actions Workflow

Create this file at `.github/workflows/firebase-distribution.yml`:

```yaml
name: Build and Distribute to Firebase

on:
  push:
    branches:
      - main
      - develop  # Add any branches you want to auto-distribute
  workflow_dispatch:  # Allow manual triggering

jobs:
  build-and-distribute:
    runs-on: ubuntu-latest
    
    steps:
    - name: Checkout repository
      uses: actions/checkout@v4
    
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: gradle
    
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    
    - name: Build Debug APK
      run: ./gradlew assembleDebug --stacktrace
    
    - name: Extract version info
      id: version
      run: |
        # Extract version from build output or use defaults
        VERSION_CODE=$(grep -oP 'versionCode\s+\K\d+' app/build.gradle || echo "1")
        VERSION_NAME=$(grep -oP 'versionName\s+"\K[^"]+' app/build.gradle || echo "1.0")
        echo "name=$VERSION_NAME" >> $GITHUB_OUTPUT
        echo "code=$VERSION_CODE" >> $GITHUB_OUTPUT
    
    - name: Upload to Firebase App Distribution
      uses: wzieba/Firebase-Distribution-Github-Action@v1
      with:
        appId: ${{ secrets.FIREBASE_APP_ID }}
        serviceCredentialsFileContent: ${{ secrets.FIREBASE_SERVICE_ACCOUNT }}
        groups: testers
        file: app/build/outputs/apk/debug/app-debug.apk
        releaseNotes: |
          Version: ${{ steps.version.outputs.name }} (Build ${{ steps.version.outputs.code }})
          
          Changes in this build:
          ${{ github.event.head_commit.message }}
          
          Commit: ${{ github.sha }}
          Branch: ${{ github.ref_name }}
```

**Note on Version Extraction:** The workflow above uses regex to extract version info from `build.gradle`. For a more robust solution, you could create custom Gradle tasks to output version info, but the regex approach works well for most standard Gradle files.

## Additional Secrets Needed

You'll need one more secret: `FIREBASE_APP_ID`

To find your Firebase App ID:
1. Go to Firebase Console
2. Select your project
3. Click the gear icon > Project settings
4. Scroll to "Your apps"
5. Find your Android app
6. Copy the "App ID" (format: `1:123456789:android:abcdef123456`)

Add it to GitHub secrets:
- Name: `FIREBASE_APP_ID`
- Value: Your Firebase App ID

## Tester Groups

In the workflow above, testers are added to a group called "testers". To create this:

1. Go to Firebase Console > App Distribution
2. Click "Testers & Groups" tab
3. Create a new group called "testers"
4. Add tester emails to this group

You can create multiple groups (e.g., "developers", "qa", "beta-users") and specify different groups in different workflows.

## Testing the Workflow

1. Commit and push the workflow file
2. Push a commit to `main` or `develop` branch
3. Check Actions tab in GitHub to see the workflow running
4. Testers will receive an email notification
5. Testers can install via Firebase App Distribution app or browser

## Alternative: Manual Upload

If you prefer to upload manually instead of automatic distribution:

```bash
# Install Firebase CLI
npm install -g firebase-tools

# Login
firebase login

# Upload APK
firebase appdistribution:distribute app/build/outputs/apk/debug/app-debug.apk \
  --app YOUR_FIREBASE_APP_ID \
  --groups testers \
  --release-notes "Manual test build"
```

## Best Practices

1. **Use Different Groups**: Separate groups for internal devs, QA, and beta testers
2. **Meaningful Release Notes**: Include what changed in each build
3. **Version Numbers**: Always increment version code for each build
4. **Test Locally First**: Don't auto-distribute every commit - consider using `workflow_dispatch` for manual control
5. **Notification Management**: Be mindful of notification frequency to avoid spamming testers

## Cost

Firebase App Distribution is **free** for:
- Unlimited apps
- Unlimited builds
- Up to 200 testers per app

Perfect for your use case!

## Testers Installation Process

1. Testers receive invitation email
2. They click "Get started" in email
3. They install Firebase App Distribution app from Play Store
4. They sign in with the email they were invited with
5. They see your app and can install with one tap
6. When you push new builds, they get notifications
7. One-tap update to latest version

## Comparison with Current Setup

| Aspect | Current (GitHub Releases + OneDrive) | Firebase App Distribution |
|--------|--------------------------------------|---------------------------|
| Upload process | Manual | Automatic |
| Phone installation | Download from OneDrive → File manager → Install | One tap in Firebase app |
| Updates | Manual repeat entire process | Notification + one tap |
| Setup time | ~5 minutes per install | ~30 seconds per install |
| Tester management | Manual sharing | Manage in Firebase Console |

## Next Steps

1. Create Firebase project
2. Set up service account
3. Add secrets to GitHub
4. Create the workflow file
5. Invite yourself as a tester
6. Push a commit to test
7. Install Firebase App Distribution app on your phone
8. Enjoy automatic distribution!

## Troubleshooting

**"App ID not found"**: Make sure you registered the Android app in Firebase Console

**"Unauthorized"**: Check that your service account has the correct roles

**"No testers notified"**: Make sure testers are in the group specified in the workflow (e.g., "testers")

**APK not found**: Check the build path matches your actual APK location

## Resources

- [Firebase App Distribution Docs](https://firebase.google.com/docs/app-distribution)
- [Firebase Distribution GitHub Action](https://github.com/wzieba/Firebase-Distribution-Github-Action)
- [Firebase CLI Reference](https://firebase.google.com/docs/cli)
