# 🏥 Pratham Chikitse — प्रथम चिकित्सा — ಪ್ರಥಮ ಚಿಕಿತ್ಸೆ

> A multilingual, offline-first emergency first-aid Android app built with Kotlin & Jetpack Compose

---

## 📱 App Overview

**Pratham Chikitse** is a stress-friendly emergency healthcare companion designed for rural and urban users in India. It provides medically clear, step-by-step first aid guidance in **English, Hindi, and Kannada** — all **offline**.

---
app-release.apk is a apk file which can be installed on android devices to use the app

## 🗂️ Project Structure

```
PrathamChikitse
│
├── .gradle
├── .idea
├── .kotlin
│
├── app
│   │
│   ├── build
│   │
│   ├── src
│   │   │
│   │   └── main
│   │       │
│   │       ├── assets
│   │       │   ├── emergency_en.json
│   │       │   ├── emergency_hi.json
│   │       │   └── emergency_kn.json
│   │       │
│   │       ├── kotlin
│   │       │   └── com.prathamchikitse
│   │       │       │
│   │       │       ├── data
│   │       │       │   └── EmergencyRepository.kt
│   │       │       │
│   │       │       ├── model
│   │       │       │   ├── emergency.kt
│   │       │       │   └── Models.kt
│   │       │       │
│   │       │       ├── navigation
│   │       │       │   └── Navigation.kt
│   │       │       │
│   │       │       ├── screens
│   │       │       │   ├── DetailScreen.kt
│   │       │       │   ├── HomeScreen.kt
│   │       │       │   ├── HospitalScreen.kt
│   │       │       │   └── SplashScreen.kt
│   │       │       │
│   │       │       ├── ui.theme
│   │       │       │   ├── Theme.kt
│   │       │       │   └── Typography.kt
│   │       │       │
│   │       │       ├── utils
│   │       │       │   ├── JsonLoader.kt
│   │       │       │   ├── LanguageManager.kt
│   │       │       │   ├── LanguageUtils.kt
│   │       │       │   └── TtsManager.kt
│   │       │       │
│   │       │       └── MainActivity.kt
│   │       │
│   │       ├── res
│   │       │   ├── drawable
│   │       │   ├── mipmap-anydpi-v26
│   │       │   ├── mipmap-hdpi
│   │       │   ├── mipmap-mdpi
│   │       │   ├── mipmap-xhdpi
│   │       │   ├── mipmap-xxhdpi
│   │       │   ├── mipmap-xxxhdpi
│   │       │   ├── values
│   │       │   ├── values-hi
│   │       │   └── values-kn
│   │       │
│   │       ├── AndroidManifest.xml
│   │       └── ic_launcher-playstore.png
│   │
│   └── build.gradle.kts
│
├── build
├── gradle
│
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── local.properties
├── README.md
└── settings.gradle.kts
...

---

## ✅ Features

### 🏠 Home Screen
- `LazyVerticalGrid` with 2-column emergency tiles
- Color-coded cards per emergency type
- Top banner with emergency number 108
- Language switcher (🌐) in top app bar
- Hospital finder button

### 🚨 20 Emergencies Covered
| Emergency | Emergency | Emergency | Emergency |
|-----------|-----------|-----------|-----------|
| 🐍 Snake Bite | 🔥 Burn | ❤️ Heart Attack | 😮 Choking |
| 🦴 Fracture | 🩸 Bleeding | ⚡ Electric Shock | 🧠 Seizure |
| ☠️ Poisoning | 🌊 Drowning | ☀️ Heat Stroke | 🐕 Dog Bite |
| 💓 CPR | 🫁 Asthma Attack | 😵 Fainting | 👃 Nose Bleed |
| 🤧 Allergic Reaction | 🦟 Insect Bite | 🤢 Food Poisoning | 🧬 Stroke |

### 📋 Detail Screen (per emergency)
- Hero card with emoji + title
- **Audio Mode button** — reads all instructions aloud via TextToSpeech
- Numbered step-by-step instructions (8 steps each)
- **✅ Do's** section with green styling
- **❌ Don'ts** section with red styling
- Emergency call banner with 108, 101, 100

### 🔊 Audio Mode (TTS)
- Uses Android `TextToSpeech` API
- Supports Kannada (`kn-IN`), Hindi (`hi-IN`), English
- Fallback to English if regional voice not installed
- Toggle start/stop with visual state change

### 🏥 Hospital Finder
- 3 simulated Bengaluru hospitals with:
  - Name, type (Govt/Private)
  - Address, distance
  - One-tap call button (dials via Intent)
- National emergency numbers: 108, 101, 100, 112, 1097

### 🌐 Multilingual
- **English** (`values/strings.xml`)
- **Hindi** (`values-hi/strings.xml`)
- **Kannada** (`values-kn/strings.xml`)
- All UI text, titles, buttons translate
- Language persists via SharedPreferences
- Applies via `Context.createConfigurationContext`

---

## 🛠️ Setup Instructions

### Prerequisites
- Android Studio Ladybug or newer
- Android SDK 35
- Kotlin 2.0+

### Steps

1. **Clone / Open Project**
   ```bash
   # Open PrathamChikitse/ folder in Android Studio
   ```

2. **Sync Gradle**
   - Click "Sync Now" when prompted
   - All dependencies auto-download

3. **Add Launcher Icons** *(optional but recommended)*
   - Replace `res/mipmap-*/ic_launcher*` with your icon
   - Or use Android Studio's Image Asset Studio (right-click `res` → New → Image Asset)

4. **Run**
   - Select device or emulator (API 24+)
   - Click ▶ Run

### Gradle Dependencies
```toml
# gradle/libs.versions.toml
navigation-compose = "2.8.5"
lifecycle-viewmodel-compose = "2.8.7"
compose-bom = "2024.11.00"
```

---

## 🎨 Design System

| Token | Value |
|-------|-------|
| Primary Color | `#D32F2F` (Emergency Red) |
| Secondary | `#2E7D32` (Safe Green) |
| Background | `#F5F5F5` (Neutral Gray) |
| Card | `#FFFFFF` |
| Typography | Material3 default scale |
| Corner Radius | 12–20dp |
| Elevation | 2–4dp |

---

## 📐 Architecture

```
MainActivity
    └── AppNavHost (Navigation Compose)
            ├── SplashScreen
            ├── HomeScreen  ←── EmergencyRepository (data object)
            ├── DetailScreen ←── TtsManager (TTS API)
            └── HospitalScreen

EmergencyRepository (Singleton data object)
    ├── List<Emergency> (20 emergencies)
    └── List<Hospital> (3 hospitals)
```

**Pattern**: Lightweight MVVM-friendly — data layer (`Repository`) → Screen composables. No ViewModel needed for this read-only offline app (can easily add later).

---

## 🔒 Permissions

```xml
<uses-permission android:name="android.permission.CALL_PHONE" />
<uses-permission android:name="android.permission.INTERNET" />
```

- `CALL_PHONE` — for one-tap hospital calling
- `INTERNET` — reserved for future GPS/maps extension

---

## 🚀 Future Enhancements

- [ ] GPS-based real hospital search (OpenStreetMap + Nominatim API)
- [ ] Offline maps integration
- [ ] Push notification for periodic safety tips
- [ ] Home screen widget for 108 quick dial
- [ ] Video demonstrations for CPR
- [ ] Tamil, Telugu, Malayalam support
- [ ] Accessibility: TalkBack full support

---


## 📜 Medical Disclaimer

> The information in this app is for emergency first-aid guidance only and is not a substitute for professional medical advice. Always call emergency services (108) immediately in life-threatening situations.

---

## 👨‍💻 Built With

- **Kotlin** + **Jetpack Compose**
- **Material3** design system
- **Navigation Compose**
- **Android TextToSpeech API**
- **Offline-first** — no internet required

---

*Designed for rural and urban users across India 🇮🇳*
