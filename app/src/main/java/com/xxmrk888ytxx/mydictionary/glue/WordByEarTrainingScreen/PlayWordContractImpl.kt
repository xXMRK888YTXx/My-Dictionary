package com.xxmrk888ytxx.mydictionary.glue.WordByEarTrainingScreen

import com.xxmrk888ytxx.mydictionary.UseCase.TextToSpeechUseCase.TextToSpeechUseCase
import com.xxmrk888ytxx.wordbyeartrainingscreen.contract.PlayWordContract
import javax.inject.Inject

class PlayWordContractImpl @Inject constructor(
    private val textToSpeechUseCase: TextToSpeechUseCase
) : PlayWordContract {

    override suspend fun play(text: String) {
        textToSpeechUseCase.speck(text)
    }
}