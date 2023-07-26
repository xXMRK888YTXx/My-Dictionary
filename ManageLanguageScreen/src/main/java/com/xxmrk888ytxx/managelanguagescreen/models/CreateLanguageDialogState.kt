package com.xxmrk888ytxx.managelanguagescreen.models

sealed class CreateLanguageDialogState {

    object Hidden : CreateLanguageDialogState()

    data class Showed(
        val languageName:String = ""
    ) : CreateLanguageDialogState()
}
