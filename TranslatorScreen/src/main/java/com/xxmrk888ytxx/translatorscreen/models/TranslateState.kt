package com.xxmrk888ytxx.translatorscreen.models

sealed interface TranslateState {

    object None : TranslateState

    object Loading : TranslateState

    data class Translated(val translatedText:String) : TranslateState

    data class Error(val errorText:Int) : TranslateState
}
