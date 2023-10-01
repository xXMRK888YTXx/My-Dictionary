package com.xxmrk888ytxx.translatorscreen.contract

interface FastSaveWordInWordGroupContract {

    suspend fun saveWord(
        wordGroupId:Int,
        word:String,
        translation:String,
        transcription:String
    ) : Result<Unit>
}