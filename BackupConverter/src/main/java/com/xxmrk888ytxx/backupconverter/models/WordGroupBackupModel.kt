package com.xxmrk888ytxx.backupconverter.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordGroupBackupModel(
    @SerialName("name") val name:String,
    @SerialName("primaryLanguageBackupId") val primaryLanguageBackupId:Int,
    @SerialName("secondaryLanguageBackupId") val secondaryLanguageBackupId:Int,
    @SerialName("words") val words:List<WordBackupModel>
)
