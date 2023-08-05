package com.xxmrk888ytxx.restorebackupscreen.contract

import android.net.Uri
import com.xxmrk888ytxx.restorebackupscreen.exception.BadBackupFileException
import com.xxmrk888ytxx.restorebackupscreen.exception.FileNotBackupFileException
import kotlin.jvm.Throws

interface RestoreBackupContract {

    @Throws(
        BadBackupFileException::class,
        FileNotBackupFileException::class
    )
    suspend fun restore(backupFile:Uri) : Result<Unit>
}