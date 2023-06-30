package com.xxmrk888ytxx.addwordscreen.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ScreenState(
    val enteredWordTextFieldText:String = "",
    val translateForEnteredWordTextFieldText:String = "",
    val transcriptTextFieldText:String = "",
    val phrasesList:ImmutableList<PhrasesModel> = persistentListOf()
)
