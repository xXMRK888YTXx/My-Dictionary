package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models

data class WordModel(
    val id: Int = 0,
    val wordGroupId: Int,
    val wordText: String,
    val translateText: String,
    val transcriptionText: String,
)
