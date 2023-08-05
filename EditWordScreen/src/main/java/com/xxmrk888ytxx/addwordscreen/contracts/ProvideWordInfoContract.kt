package com.xxmrk888ytxx.addwordscreen.contracts

import com.xxmrk888ytxx.addwordscreen.models.WordModel

/**
 * [Ru]
 * Контракт для предоставления информации о слове
 */

/**
 * [En]
 * Contract to provide information about the word
 */
interface ProvideWordInfoContract {

    suspend fun getWordInfo(wordId:Int) : WordModel
}