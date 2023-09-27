package com.xxmrk888ytxx.translatorscreen.contract

interface TextToSpeechContract {

    suspend fun textToSpeech(text:String)
}