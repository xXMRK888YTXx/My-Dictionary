package com.xxmrk888ytxx.addwordscreen.contracts

import com.xxmrk888ytxx.addwordscreen.models.PhrasesModel


interface ProvideWordPhrasesContract {

    suspend fun getPhrases(wordId:Int) : List<PhrasesModel>
}