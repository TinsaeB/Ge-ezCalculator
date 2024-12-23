package com.ttbzrs.geezcalculator

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TextToSpeechEngine(context: Context) {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    init {
        initialize(context)
    }

    private fun initialize(context: Context) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val amharicLocale = Locale("am", "ET") // Amharic (Ethiopia)
                val result = tts?.setLanguage(amharicLocale)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TextToSpeechEngine", "Amharic language is not supported or missing data")
                } else {
                    isInitialized = true
                }
            } else {
                Log.e("TextToSpeechEngine", "Initialization failed")
            }
        }
    }

    fun speak(text: String) {
        if (isInitialized) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        } else {
            Log.e("TextToSpeechEngine", "TTS not initialized")
        }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        isInitialized = false
    }
}