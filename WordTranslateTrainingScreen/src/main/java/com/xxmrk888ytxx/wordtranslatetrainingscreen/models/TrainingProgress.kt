package com.xxmrk888ytxx.wordtranslatetrainingscreen.models

data class TrainingProgress(
    val correctAnswers:Int = 0,
    val incorrectAnswers:Int = 0,
    val currentPage:Int = 0,
    val currentAnswer:String = ""
)
