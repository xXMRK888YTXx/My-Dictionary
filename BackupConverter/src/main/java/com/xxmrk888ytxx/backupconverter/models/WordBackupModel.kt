package com.xxmrk888ytxx.backupconverter.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordBackupModel(
    @SerialName("wordText") val wordText:String,
    @SerialName("wordTranslate") val wordTranslate:String,
    @SerialName("wordTranscription")  val wordTranscription:String,
    @SerialName("phrases")  val phrases:List<PhrasesBackupModel>
)