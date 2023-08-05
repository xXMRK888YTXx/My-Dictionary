package com.xxmrk888ytxx.wordgroupscreen.models

sealed class WordGroupDialogOptionState {

    object Hidden : WordGroupDialogOptionState()

    data class Showed(val wordGroupId:Int,val isHaveImage:Boolean) : WordGroupDialogOptionState()
}
