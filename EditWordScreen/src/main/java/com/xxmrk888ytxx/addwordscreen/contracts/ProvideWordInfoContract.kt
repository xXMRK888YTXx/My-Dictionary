package com.xxmrk888ytxx.addwordscreen.contracts

import com.xxmrk888ytxx.addwordscreen.models.WordModel

interface ProvideWordInfoContract {

    suspend fun getWordInfo(wordId:Int) : WordModel
}