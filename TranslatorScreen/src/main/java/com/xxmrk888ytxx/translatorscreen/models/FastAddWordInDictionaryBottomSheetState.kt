package com.xxmrk888ytxx.translatorscreen.models

sealed interface FastAddWordInDictionaryBottomSheetState {

    object Hidden : FastAddWordInDictionaryBottomSheetState

    data class Showed(
        val originalWord:String,
        val translation:String,
        val transcription:String = "",
        val isSelectingWordGroupState:Boolean = false,
        val selectedWordGroup: WordGroup? = null
    ) : FastAddWordInDictionaryBottomSheetState
}