package com.prathamchikitse.utils

import android.content.Context
import org.json.JSONObject

object LanguageManager {

    private var data: JSONObject = JSONObject()

    fun setLanguage(context: Context, lang: String) {
        val file = when (lang) {
            "hi" -> "emergency_hi.json"
            "kn" -> "emergency_kn.json"
            else -> "emergency_en.json"
        }

        data = JsonLoader.loadJson(context, file)
    }

    fun get(key: String): String {
        return data.optString(key, key)
    }
}