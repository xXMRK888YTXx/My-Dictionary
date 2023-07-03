package com.xxmrk888ytxx.viewgroupwordsscreen.models

sealed class WordOptionDialogState {

    object Hidden : WordOptionDialogState()

    data class Showed(val wordId:Int) : WordOptionDialogState()
}
