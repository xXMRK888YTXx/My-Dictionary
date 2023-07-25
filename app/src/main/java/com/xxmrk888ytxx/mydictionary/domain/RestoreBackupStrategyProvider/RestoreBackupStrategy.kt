package com.xxmrk888ytxx.mydictionary.domain.RestoreBackupStrategyProvider

import java.io.File

interface RestoreBackupStrategy {

    suspend fun restore(backupDir: File)
}