# GitHub Copilot Instructions

## Project Overview

This repository contains an Android application for dictating notes while driving using Android Auto. The app enables safe, hands-free note-taking by leveraging voice input and Android Auto's driving-optimized interface.

## Technology Stack

- **Platform**: Android
- **Language**: Java/Kotlin (to be determined based on project setup)
- **Key Technologies**: 
  - Android Auto SDK
  - Speech-to-Text API
  - Android Voice Recognition

## Development Setup

### Prerequisites
- Android Studio (latest stable version)
- Android SDK (API level 21+ for Android Auto compatibility)
- Android Auto Development Device or Emulator
- Java Development Kit (JDK 11 or later)

### Build Instructions
```bash
# Clone the repository
git clone <repository-url>
cd studious-garbanzo

# Build the project using Gradle wrapper
./gradlew build

# Run tests
./gradlew test

# Install on device/emulator
./gradlew installDebug
```

## Code Conventions

### Android Specific
- Follow [Android Code Style Guidelines](https://developer.android.com/kotlin/style-guide) for Kotlin
- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) for Java
- Use Android Architecture Components (ViewModel, LiveData, Room) where appropriate
- Follow MVVM or MVI architecture pattern for clean separation of concerns

### General Practices
- Write clear, self-documenting code with meaningful variable and function names
- Add comments only when necessary to explain complex logic or business rules
- Keep functions small and focused on a single responsibility
- Write unit tests for business logic and integration tests for UI components
- Handle permissions properly, especially for microphone access
- Ensure app follows Android Auto's design and distraction guidelines

## Testing

### Unit Tests
- Place unit tests in `src/test/java/` directory
- Use JUnit 4 or JUnit 5 for unit testing
- Mock Android dependencies using Mockito or MockK

### Instrumentation Tests
- Place instrumentation tests in `src/androidTest/java/` directory
- Use Espresso for UI testing
- Test Android Auto specific features in appropriate test environments

### Running Tests
```bash
# Run unit tests
./gradlew test

# Run instrumentation tests
./gradlew connectedAndroidTest
```

## Android Auto Specific Guidelines

### Safety First
- Ensure all interactions are optimized for driving scenarios
- Minimize user interaction and maximize voice control
- Follow Android Auto's distraction guidelines strictly
- Test in driving scenarios or simulators

### Voice Input
- Implement robust error handling for speech recognition failures
- Provide audio feedback for user actions
- Support offline functionality where possible

## Pull Request Guidelines

- Keep PRs focused and atomic
- Include test coverage for new features
- Update documentation when adding new functionality
- Ensure all tests pass before requesting review
- Follow semantic commit messages (feat:, fix:, docs:, etc.)

## Common Tasks

### Adding a New Feature
1. Create a new branch from `main`
2. Implement the feature following architecture patterns
3. Add appropriate tests (unit and/or integration)
4. Update documentation if needed
5. Create a PR with clear description

### Fixing a Bug
1. Write a failing test that reproduces the bug
2. Fix the bug
3. Ensure the test now passes
4. Add regression tests if appropriate

### Updating Dependencies
1. Check compatibility with current Android API levels
2. Review changelog for breaking changes
3. Update gradle dependencies
4. Run full test suite
5. Test on physical devices when possible

## Resources

- [Android Auto Documentation](https://developer.android.com/training/cars)
- [Android Development Best Practices](https://developer.android.com/jetpack/guide)
- [Material Design Guidelines](https://material.io/design)
- [Android Auto Design Guidelines](https://developer.android.com/training/cars/apps)

## Notes for Copilot

- Prioritize user safety when implementing driving-related features
- Always consider Android Auto's strict UI and interaction limitations
- Ensure proper permission handling for microphone and storage
- Test voice recognition in various noise conditions
- Keep the app lightweight and responsive
- Follow Android's battery optimization guidelines
