package com.xxmrk888ytxx.database.models

data class WordGroupLocalModel(
    val id: Int,
    val name:String,
    val primaryLanguageId: Int,
    val secondaryLanguageId: Int,
    val imageUrl:String?
)