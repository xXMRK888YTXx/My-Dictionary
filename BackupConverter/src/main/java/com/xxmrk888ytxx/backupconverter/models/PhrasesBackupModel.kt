package com.xxmrk888ytxx.backupconverter.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhrasesBackupModel(
    @SerialName("phrasesText") val phrasesText:String,
    @SerialName("phrasesTranslate") val phrasesTranslate: String
)
