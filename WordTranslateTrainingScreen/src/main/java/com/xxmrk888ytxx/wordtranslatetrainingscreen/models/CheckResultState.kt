package com.xxmrk888ytxx.wordtranslatetrainingscreen.models

sealed class CheckResultState {

    object None : CheckResultState()

    data class Failed(val correctAnswer:String) : CheckResultState()

    object Correct : CheckResultState()
}
