package com.xxmrk888ytxx.createwordgroupscreen.models

sealed class CreateNewLanguageDialogState {

    object Hidden : CreateNewLanguageDialogState()

    data class Showed(
        val newLanguageText: String = "",
        val isAddingInProcess:Boolean = false
    ) : CreateNewLanguageDialogState()
}
