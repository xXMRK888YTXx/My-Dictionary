package com.xxmrk888ytxx.database.models

import androidx.room.PrimaryKey

data class WordLocalModel(
    val id: Int = 0,
    val wordGroupId: Int,
    val wordText: String,
    val translateText: String,
    val transcriptionText: String,
)