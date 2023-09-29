package com.xxmrk888ytxx.translatorscreen.models

data class ScreenState(
    val textForState:String = "",
    val currentOriginalLanguage:SupportedLanguage = SupportedLanguage("en",0),
    val currentLanguageForTranslate:SupportedLanguage = SupportedLanguage("ru",0)
)
