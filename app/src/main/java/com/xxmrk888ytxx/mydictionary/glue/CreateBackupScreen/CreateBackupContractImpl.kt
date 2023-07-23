package com.xxmrk888ytxx.mydictionary.glue.CreateBackupScreen

import android.net.Uri
import com.xxmrk888ytxx.createbackupscreen.contract.CreateBackupContract
import com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupUseCase.CreateBackupUseCase
import javax.inject.Inject

class CreateBackupContractImpl @Inject constructor(
    private val createBackupUseCase: CreateBackupUseCase
) : CreateBackupContract {


    override suspend fun createBackup(
        selectedWordGroupsId: Set<Int>,
        backupFileUri: Uri,
    ): Result<Unit> = createBackupUseCase.execute(selectedWordGroupsId, backupFileUri)
}