# 🏥 Pratham Chikitse — प्रथम चिकित्सा — ಪ್ರಥಮ ಚಿಕಿತ್ಸೆ

> A multilingual, offline-first emergency first-aid Android app built with Kotlin & Jetpack Compose

---

## 📱 App Overview

**Pratham Chikitse** is a stress-friendly emergency healthcare companion designed for rural and urban users in India. It provides medically clear, step-by-step first aid guidance in **English, Hindi, and Kannada** — all **offline**.

---

## 🗂️ Project Structure

```
app/src/main/
├── kotlin/com/prathamchikitse/
│   ├── MainActivity.kt             # Entry point, language context switching
│   ├── data/
│   │   └── EmergencyRepository.kt  # All 20 emergency data + hospital data
│   ├── model/
│   │   └── Models.kt               # Emergency, Hospital, AppLanguage data classes
│   ├── navigation/
│   │   └── Navigation.kt           # NavHost, routes, screen composition
│   ├── screens/
│   │   ├── SplashScreen.kt         # Animated splash with app branding
│   │   ├── HomeScreen.kt           # Grid of emergencies + language switcher
│   │   ├── DetailScreen.kt         # Step-by-step guide + TTS audio mode
│   │   └── HospitalScreen.kt       # Nearby hospitals + emergency numbers
│   ├── ui/theme/
│   │   ├── Theme.kt                # Material3 color scheme (emergency red)
│   │   └── Typography.kt           # Accessible font sizes
│   └── utils/
│       ├── TtsManager.kt           # Android TextToSpeech wrapper
│       └── LanguageUtils.kt        # Locale context helper
└── res/
    ├── values/strings.xml          # English strings
    ├── values-hi/strings.xml       # Hindi strings
    └── values-kn/strings.xml       # Kannada strings
```

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
