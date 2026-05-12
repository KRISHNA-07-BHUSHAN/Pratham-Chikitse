package com.prathamchikitse.utils

import android.content.Context
import android.content.res.Configuration
import com.prathamchikitse.model.AppLanguage
import java.util.Locale

object LanguageUtils {

    fun updateLocale(context: Context, language: AppLanguage): Context {
        val locale = Locale(language.code)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    fun getEmergencyTitle(context: Context, titleKey: String): String {
        val resId = context.resources.getIdentifier(
            "emergency_$titleKey", "string", context.packageName
        )
        return if (resId != 0) context.getString(resId) else titleKey.replace("_", " ").capitalize()
    }

    fun getStepText(context: Context, emergencyKey: String, stepIndex: Int): String {
        val resId = context.resources.getIdentifier(
            "step_${emergencyKey}_$stepIndex", "string", context.packageName
        )
        return if (resId != 0) context.getString(resId) else ""
    }
}

fun String.capitalize(): String {
    return this.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}
