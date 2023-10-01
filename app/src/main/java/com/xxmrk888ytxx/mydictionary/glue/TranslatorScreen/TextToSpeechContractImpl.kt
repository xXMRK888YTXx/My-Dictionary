package com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen

import com.xxmrk888ytxx.mydictionary.UseCase.TextToSpeechUseCase.TextToSpeechUseCase
import com.xxmrk888ytxx.translatorscreen.contract.TextToSpeechContract
import javax.inject.Inject

class TextToSpeechContractImpl @Inject constructor(
    private val textToSpeechUseCase: TextToSpeechUseCase
) : TextToSpeechContract {

    override suspend fun textToSpeech(text: String) {
        textToSpeechUseCase.speck(text)
    }
}