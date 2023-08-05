package com.xxmrk888ytxx.backupconverter.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LanguagesBackupModel(
    @SerialName("backupId") val backupId:Int,
    @SerialName("name") val name:String
)
