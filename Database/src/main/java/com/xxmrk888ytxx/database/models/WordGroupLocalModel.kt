package com.xxmrk888ytxx.database.models

data class WordGroupLocalModel(
    val id: Int,
    val name:String,
    val primaryLanguage: LanguageLocalModel,
    val secondaryLanguage: LanguageLocalModel,
    val imageUrl:String?
)