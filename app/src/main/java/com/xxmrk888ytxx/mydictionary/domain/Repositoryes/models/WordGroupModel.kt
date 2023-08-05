package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models

data class WordGroupModel(
    val id:Int,
    val name:String,
    val primaryLanguageId: Int,
    val secondaryLanguageId: Int,
    val imageUrl:String?
)
