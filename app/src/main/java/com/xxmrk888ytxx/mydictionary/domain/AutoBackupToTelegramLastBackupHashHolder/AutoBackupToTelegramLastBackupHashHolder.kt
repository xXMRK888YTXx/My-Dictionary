package com.xxmrk888ytxx.mydictionary.domain.AutoBackupToTelegramLastBackupHashHolder

import kotlin.jvm.Throws

interface AutoBackupToTelegramLastBackupHashHolder {

    suspend fun updateHash()

    @Throws(ApplicationDataIsEmptyException::class)
    suspend fun isHaveChanges() : Boolean

    suspend fun reset()
}