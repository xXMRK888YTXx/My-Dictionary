package com.xxmrk888ytxx.database.models

data class WordGroupLocalModel(
    val id: Int,
    val primaryLanguage: LanguageLocalModel,
    val secondaryLanguage: LanguageLocalModel,
    val imageUrl:String?
)