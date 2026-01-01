# Test Documentation

## Overview

This directory contains unit tests for the Voice Notes Android application. The tests are designed to protect existing functionality and ensure code quality.

## Testing Framework

We use the following testing frameworks:

### Primary Frameworks
- **JUnit 4** (4.13.2) - Industry-standard testing framework for Java/Kotlin
- **Robolectric** (4.10.3) - Allows Android unit tests to run on the JVM without an emulator
- **MockK** (1.13.5) - Kotlin-first mocking library for test doubles

### Supporting Libraries
- **AndroidX Test Core** (1.5.0) - Core testing utilities
- **AndroidX Test JUnit** (1.1.5) - JUnit extensions for Android
- **Espresso** (3.5.1) - UI testing framework (configured for future use)

## Why These Frameworks?

### JUnit 4
- Most widely used testing framework in the Android ecosystem
- Excellent IDE support (Android Studio, IntelliJ IDEA)
- Large community and extensive documentation
- Simple annotations (`@Test`, `@Before`, `@After`)

### Robolectric
- Runs Android tests on the JVM (much faster than emulator/device tests)
- Allows testing of Android framework components (Activities, Views, etc.)
- No need for physical device or emulator during development
- Perfect for unit tests that need Android context

### MockK
- Built for Kotlin with idiomatic Kotlin syntax
- Better null safety compared to Java-based mocking frameworks
- Cleaner DSL for creating mocks and stubs
- First-class support for Kotlin features (coroutines, extension functions, etc.)

## Test Structure

### MainActivityTest.kt
Tests the main activity functionality:
- Activity initialization and lifecycle
- UI element verification (buttons, text views)
- Click listener attachment
- Permission and request code constants

### ConstantsAndUtilsTest.kt
Tests utility functions and constants:
- Date/time formatting for notes and exports
- Markdown export format validation
- Filename generation logic
- String formatting patterns

## Running Tests

### Run All Tests
```bash
./gradlew test
```

### Run Specific Test Suite
```bash
./gradlew testDebugUnitTest
```

### Run with Coverage
```bash
./gradlew testDebugUnitTest jacocoTestReport
```

### Run Specific Test Class
```bash
./gradlew test --tests com.voicenotes.MainActivityTest
```

### Run Specific Test Method
```bash
./gradlew test --tests com.voicenotes.MainActivityTest.testActivityInitialization
```

## Test Coverage

Current test coverage protects:
- ✅ Activity initialization and setup
- ✅ UI component verification
- ✅ Event listener attachment
- ✅ Timestamp formatting
- ✅ Markdown export formatting
- ✅ Request code constants
- ✅ Note formatting and structure

## Adding New Tests

When adding new functionality, follow these guidelines:

### 1. Choose the Right Test Type
- **Unit tests** (src/test/): Test business logic, utilities, formatting
- **Instrumentation tests** (src/androidTest/): Test UI interactions, database, device features

### 2. Follow Naming Conventions
- Test classes: `[ClassName]Test.kt`
- Test methods: `test[FeatureName]`
- Be descriptive: `testVoiceButtonHasClickListener` not `testButton1`

### 3. Test Structure (AAA Pattern)
```kotlin
@Test
fun testFeatureName() {
    // Arrange - Set up test data and conditions
    val input = "test data"
    
    // Act - Execute the code under test
    val result = functionToTest(input)
    
    // Assert - Verify the results
    assertEquals(expectedValue, result)
}
```

### 4. Use Descriptive Assertions
```kotlin
// Good
assertTrue("Voice button should have a click listener", voiceButton.hasOnClickListeners())

// Avoid
assertTrue(voiceButton.hasOnClickListeners())
```

## Best Practices

1. **Keep tests independent**: Each test should run in isolation
2. **Use @Before and @After**: Set up and clean up properly
3. **Test one thing per test**: Each test should verify one behavior
4. **Make tests readable**: Tests are documentation
5. **Use meaningful names**: Test names should describe what they test
6. **Don't test Android framework**: Trust that TextView works, test YOUR code
7. **Mock external dependencies**: Use MockK for dependencies
8. **Fast tests**: Unit tests should run in milliseconds

## Continuous Integration

Tests are automatically run on:
- Every pull request
- Every commit to main branch
- Before releases

All tests must pass before code can be merged.

## Future Improvements

Potential areas for expansion:
- [ ] Add Espresso UI tests for critical user flows
- [ ] Add integration tests for file I/O operations
- [ ] Add tests for speech recognition flow (with mocked SpeechRecognizer)
- [ ] Add tests for permission handling edge cases
- [ ] Add performance tests for large note collections
- [ ] Set up code coverage reporting (JaCoCo)
- [ ] Add snapshot testing for UI components

## Resources

- [JUnit 4 Documentation](https://junit.org/junit4/)
- [Robolectric Documentation](http://robolectric.org/)
- [MockK Documentation](https://mockk.io/)
- [Android Testing Guide](https://developer.android.com/training/testing)
- [Testing Best Practices](https://developer.android.com/training/testing/fundamentals)

## Questions?

If you have questions about the testing setup or need help writing tests:
1. Check this README
2. Look at existing test examples in this directory
3. Refer to the framework documentation linked above
4. Ask the team in your pull request
