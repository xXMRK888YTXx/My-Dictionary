package com.xxmrk888ytxx.wordgroupscreen.models


data class WordGroup(
    val id:Int,
    val name:String,
    val primaryLanguage:Language,
    val secondaryLanguage: Language,
    val imageUrl:String?
)
