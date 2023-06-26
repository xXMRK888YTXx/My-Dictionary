package com.xxmrk888ytxx.mydictionary.models

data class WordGroupModel(
    val id:Int,
    val name:String,
    val primaryLanguage: LanguageModel,
    val secondaryLanguage: LanguageModel,
    val imageUrl:String?
)
