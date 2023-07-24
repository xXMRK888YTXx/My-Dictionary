package com.xxmrk888ytxx.backupconverter.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BackupHeader(
    @SerialName("version") val version:Int,
    @SerialName("createTime") val createTime:Long
)
