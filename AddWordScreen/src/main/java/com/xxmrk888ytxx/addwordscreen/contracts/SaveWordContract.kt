package com.xxmrk888ytxx.addwordscreen.contracts

interface SaveWordContract {

    suspend fun saveWord(
        wordGroup:Int,
        wordText: String,
        translateWordText: String,
        transcriptText: String,
    ): Int
}