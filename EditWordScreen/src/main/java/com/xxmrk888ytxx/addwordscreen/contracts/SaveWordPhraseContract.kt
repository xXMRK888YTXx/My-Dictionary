package com.xxmrk888ytxx.addwordscreen.contracts

interface SaveWordPhraseContract {

    suspend fun savePhrase(
        wordId:Int,
        phraseId:Int,
        phraseText:String,
        translatePhrase:String
    )
}