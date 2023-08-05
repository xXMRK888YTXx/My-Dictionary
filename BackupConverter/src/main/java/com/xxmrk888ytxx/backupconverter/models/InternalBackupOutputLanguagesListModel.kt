package com.xxmrk888ytxx.backupconverter.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InternalBackupOutputLanguagesListModel(
    @SerialName("languagesList") val languagesList: List<LanguagesBackupModel>
)
