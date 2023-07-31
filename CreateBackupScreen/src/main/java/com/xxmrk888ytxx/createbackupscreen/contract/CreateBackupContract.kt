package com.xxmrk888ytxx.createbackupscreen.contract

import android.net.Uri

/**
 * [Ru]
 * Контракт для создания резервной копии
 */

/**
 * [En]
 * Contract for creating a backup
 */
interface CreateBackupContract {

    suspend fun createBackup(
        selectedWordGroupsId:Set<Int>,
        backupFileUri:Uri
    ) : Result<Unit>
}