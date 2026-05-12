package com.prathamchikitse.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.prathamchikitse.model.AppLanguage
import java.util.Locale

class TtsManager(private val context: Context) {

    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var isSpeaking = false
    private var onSpeakingChanged: ((Boolean) -> Unit)? = null

    init {
        initializeTts()
    }

    private fun initializeTts() {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isInitialized = true
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        isSpeaking = true
                        onSpeakingChanged?.invoke(true)
                    }

                    override fun onDone(utteranceId: String?) {
                        isSpeaking = false
                        onSpeakingChanged?.invoke(false)
                    }

                    override fun onError(utteranceId: String?) {
                        isSpeaking = false
                        onSpeakingChanged?.invoke(false)
                    }
                })
            } else {
                Log.e("TtsManager", "TextToSpeech initialization failed")
            }
        }
    }

    fun setOnSpeakingChanged(callback: (Boolean) -> Unit) {
        onSpeakingChanged = callback
    }

    fun speak(text: String, language: AppLanguage) {
        if (!isInitialized) return

        val locale = when (language) {
            AppLanguage.KANNADA -> Locale("kn", "IN")
            AppLanguage.HINDI -> Locale("hi", "IN")
            AppLanguage.ENGLISH -> Locale.ENGLISH
        }

        val result = tts?.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            // Fallback to English
            tts?.setLanguage(Locale.ENGLISH)
        }

        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "PC_UTTERANCE_${System.currentTimeMillis()}")
    }

    fun stop() {
        tts?.stop()
        isSpeaking = false
        onSpeakingChanged?.invoke(false)
    }

    fun isCurrentlySpeaking() = isSpeaking

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isInitialized = false
    }
}
