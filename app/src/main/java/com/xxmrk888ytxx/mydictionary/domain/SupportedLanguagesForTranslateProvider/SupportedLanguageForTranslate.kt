package com.xxmrk888ytxx.mydictionary.domain.SupportedLanguagesForTranslateProvider

import androidx.annotation.IdRes

data class SupportedLanguageForTranslate(
    val code:String,
    @IdRes val name:Int
)
