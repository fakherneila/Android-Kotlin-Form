# AtelierAndroid - TP02

Android application developed in Kotlin for the Mobile Development module (TP02). This project consists of a complete registration form for an Android workshop, featuring strict input validation, dynamic UI updates, and a bonus feature for managing available seats per group.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup & Configuration](#setup--configuration)
- [Build & Install](#build--install)
- [Test Scenarios](#test-scenarios)
- [Troubleshooting](#troubleshooting)
- [Author](#author)

---

## Features

### Core Form

- **Registration form**: Collects Name, Surname, Age, Group (Spinner), Test Support (RadioGroup), and a Confirmation Checkbox.
- **Input validation**: Checks for empty fields, valid age (1-120), group selection, and checkbox confirmation.
- **Safe type conversion**: Uses `toIntOrNull()` to prevent app crashes on invalid age input.
- **Dynamic display**: Generates a formatted summary using Kotlin string interpolation.
- **Reset**: Clears the form fields and selections without affecting the seat counters.
- **Scrolling**: The UI is wrapped in a `ScrollView` so fields remain accessible on small screens or with the keyboard open.

### Bonus Feature — Seat Management

- **Seat tracking**: Manages 3 available seats for each group (G1, G2, G3).
- **Dynamic counter**: Updates the `tvPlaces` TextView after every successful registration.
- **Full group check**: Displays a "Groupe complet" Toast and blocks the registration if the chosen group has no seats left.
- **"COMPLET" display**: Shows "COMPLET" in red next to a group's counter when it reaches zero.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| Platform | Android (API 24+) |
| UI | XML Layouts (`ScrollView` + `LinearLayout`) |
| Build System | Gradle 8.5 |
| IDE | VS Code (without Android Studio) |
| Target SDK | 34 |
| JDK | Java 21 |

---

## Project Structure

```text
AtelierAndroid/
├── app/
│   ├── src/main/
│   │   ├── java/tn/tp/atelierandroid/
│   │   │   └── MainActivity.kt       # Activity logic, validations, and seat counters
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml # Form layout (ScrollView + LinearLayout)
│   │   │   └── values/
│   │   │       ├── strings.xml       # Text resources
│   │   │       └── arrays.xml        # Group list for the Spinner
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── build.gradle.kts
├── gradle.properties
├── local.properties                  # SDK path (not committed)
└── README.md
```

---

## Prerequisites

- **Java 21**: Required by the Android Gradle Plugin (AGP 8.2.2).
- **Android SDK**: Build-Tools 34 and Platform 34 must be installed.
- **ADB**: Installed and configured to communicate with a physical device or emulator.
- **Android device**: A phone or emulator running Android 7.0 (API 24) or higher.

---

## Setup & Configuration

1. **Clone the repository:**

   ```bash
   git clone https://github.com/fakherneila/Android-Kotlin-Form.git
   cd Android-Kotlin-Form
   ```

2. **Configure the Java path:**
   Ensure `gradle.properties` points to your JDK 21 installation:

   ```properties
   org.gradle.java.home=/usr/lib/jvm/java-21-openjdk
   android.useAndroidX=true
   ```

3. **Configure the Android SDK path:**
   Create a `local.properties` file at the root of the project and add your SDK path:

   ```properties
   sdk.dir=/opt/android-sdk
   ```

---

## Build & Install

1. **Build the debug APK:**

   ```bash
   ./gradlew clean assembleDebug
   ```

2. **Install on a connected device:**

   ```bash
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   ```

3. **Launch the application:**

   ```bash
   adb shell am start -n tn.tp.atelierandroid/.MainActivity
   ```

---

## Test Scenarios

### Core Form Tests

| ID | Scenario | Expected Result |
|----|----------|-----------------|
| T01 | Empty Name | Toast displayed; no summary |
| T02 | Age = abc | Toast displayed; no crash |
| T03 | Age = 0 | Toast displayed; age rejected |
| T04 | Group = "Choisir un groupe" | Toast displayed; no summary |
| T05 | Checkbox unchecked | Toast displayed; no summary |
| T06 | Complete valid form | Full summary displayed |
| T07 | Click "Effacer" after T06 | Fields and choices reset |
| T08 | Narrow screen / keyboard open | Fields scrollable |

### Bonus Seat Management Tests

| ID | Scenario | Expected Result |
|----|----------|-----------------|
| B01 | App launched | G1 = 3, G2 = 3, G3 = 3 |
| B02 | Valid registration in G1 | G1 = 2; summary displayed |
| B03 | 2 more valid registrations in G1 | G1 = 0; others unchanged |
| B04 | New registration in G1 | Toast "Groupe complet"; G1 stays 0 |
| B05 | Valid registration in G2 | G2 = 2; G1 stays 0 |
| B06 | Click "Effacer" | Fields cleared; seats preserved |
| B07 | Invalid input in G3 (empty age) | Error; G3 stays 3 |
| B08 | G1 reaches 0 | "COMPLET" displayed in red |

---

## Troubleshooting

| Symptom | Probable Cause | Solution |
|---------|----------------|----------|
| "Android Gradle plugin requires Java 17" | System default is Java 11 | Set `org.gradle.java.home` to Java 21 in `gradle.properties` |
| "Cannot mutate the dependencies" | Incompatible Gradle version | Use Gradle 8.5 (not 9.x) in `gradle-wrapper.properties` |
| "Unresolved reference: R" | XML layout or IDs incorrect | Verify XML IDs and rebuild with `./gradlew clean assembleDebug` |
| App crashes on "Afficher" | `toInt()` used instead of `toIntOrNull()` | Replace `toInt()` with `toIntOrNull()` in `MainActivity.kt` |
| "Invalid unicode escape sequence" | Raw apostrophe in `strings.xml` | Escape it as `\'` (e.g., `d\'inscription`) |
| App does not launch | Wrong ADB command | Use `adb shell am start -n tn.tp.atelierandroid/.MainActivity` |

---

## Author

**Fakher Neila**

- Class: ING-A3-GL-AL-04
- Module: Mobile Development
- Repository: <https://github.com/fakherneila/Android-Kotlin-Form>
- Year: 2026
