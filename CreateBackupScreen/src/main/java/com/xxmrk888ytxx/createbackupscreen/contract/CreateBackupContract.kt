package com.xxmrk888ytxx.createbackupscreen.contract

import android.net.Uri

interface CreateBackupContract {

    suspend fun createBackup(
        selectedWordGroupsId:Set<Int>,
        backupFileUri:Uri
    ) : Result<Unit>
}