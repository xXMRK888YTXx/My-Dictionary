package com.xxmrk888ytxx.basetrainingcomponents.models

sealed class CheckResultState {

    object None : CheckResultState()

    data class Failed(val correctAnswer:String) : CheckResultState()

    object Correct : CheckResultState()
}
