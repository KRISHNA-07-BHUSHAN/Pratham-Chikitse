package com.prathamchikitse.model


data class Hospital(
    val name: String,
    val phone: String,
    val address: String,
    val distance: String,
    val type: String
)

enum class AppLanguage(val code: String, val displayName: String, val localeName: String) {
    ENGLISH("en", "English", "en"),
    HINDI("hi", "हिंदी", "hi"),
    KANNADA("kn", "ಕನ್ನಡ", "kn")
}
