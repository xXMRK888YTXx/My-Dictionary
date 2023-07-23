package com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupUseCase

import android.net.Uri

interface CreateBackupUseCase {

    suspend fun execute(
        backupWordGroupsId:Set<Int>,
        fileUri:Uri
    ) : Result<Unit>
}