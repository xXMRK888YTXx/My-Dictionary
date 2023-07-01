package com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen

import com.xxmrk888ytxx.texttospeechmanager.TTSManager
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.TextToSpeechContract
import javax.inject.Inject

class TextToSpeechContractImpl @Inject constructor(
    private val ttsManager: TTSManager
) : TextToSpeechContract {

    override fun speck(text: String) {
        ttsManager.speck(text)
    }
}