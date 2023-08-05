package com.xxmrk888ytxx.mydictionary.UseCase.TextToSpeechUseCase

import com.xxmrk888ytxx.languageindificator.LanguageDeterminant
import com.xxmrk888ytxx.texttospeechmanager.TTSManager
import javax.inject.Inject

class TextToSpeechUseCaseImpl @Inject constructor(
    private val ttsManager: TTSManager,
    private val languageDeterminant: LanguageDeterminant
) : TextToSpeechUseCase {

    override suspend fun speck(text: String) {
        val languageCode:String? = try {
            languageDeterminant.getLanguageCodeFromText(text)
        } catch (e:Exception) { null }

        ttsManager.speck(text = text,languageCode = languageCode)
    }
}