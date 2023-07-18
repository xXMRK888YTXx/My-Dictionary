package com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen

import com.xxmrk888ytxx.mydictionary.UseCase.TextToSpeechUseCase.TextToSpeechUseCase
import com.xxmrk888ytxx.texttospeechmanager.TTSManager
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.TextToSpeechContract
import javax.inject.Inject

class TextToSpeechContractImpl @Inject constructor(
    private val textToSpeechUseCase: TextToSpeechUseCase
) : TextToSpeechContract {

    override suspend fun speck(text: String) {
        textToSpeechUseCase.speck(text)
    }
}