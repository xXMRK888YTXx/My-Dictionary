package com.xxmrk888ytxx.translatorscreen.models

sealed interface ChangeLanguageBottomSheetState {

    object Hidden : ChangeLanguageBottomSheetState

    object ChangingOriginalLanguage : ChangeLanguageBottomSheetState

    object ChangingLanguageForTranslate : ChangeLanguageBottomSheetState
}