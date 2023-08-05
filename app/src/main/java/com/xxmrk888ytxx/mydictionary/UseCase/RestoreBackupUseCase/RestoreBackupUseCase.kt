package com.xxmrk888ytxx.mydictionary.UseCase.RestoreBackupUseCase

import android.net.Uri

interface RestoreBackupUseCase {

    suspend fun execute(backupFileUri:Uri) : Result<Unit>
}