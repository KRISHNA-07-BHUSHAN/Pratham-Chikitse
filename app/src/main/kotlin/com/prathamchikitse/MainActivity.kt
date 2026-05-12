package com.prathamchikitse

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.prathamchikitse.data.EmergencyRepository
import com.prathamchikitse.model.AppLanguage
import com.prathamchikitse.navigation.AppNavHost
import com.prathamchikitse.ui.theme.PrathamChikitseTheme
import com.prathamchikitse.utils.LanguageManager
import com.prathamchikitse.utils.LanguageUtils

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val savedLangCode = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            .getString("language", AppLanguage.ENGLISH.code) ?: AppLanguage.ENGLISH.code
        val initialLang = AppLanguage.values().find { it.code == savedLangCode } ?: AppLanguage.ENGLISH
        EmergencyRepository.loadLanguage(this, savedLangCode)
        LanguageManager.setLanguage(this, savedLangCode)
        setContent {
            var currentLanguage by remember { mutableStateOf(initialLang) }

            PrathamChikitseTheme {
                val navController = rememberNavController()

                AppNavHost(
                    navController = navController,
                    currentLanguage = currentLanguage,
                    onLanguageChange = { newLang ->
                        currentLanguage = newLang

                        getSharedPreferences("prefs", Context.MODE_PRIVATE)
                            .edit()
                            .putString("language", newLang.code)
                            .apply()

                        // ✅ RELOAD EMERGENCY JSON ALSO
                        EmergencyRepository.loadLanguage(this, newLang.code)

                        LanguageManager.setLanguage(this, newLang.code)

                        recreate()
                    }
                )
            }
        }
    }
}
