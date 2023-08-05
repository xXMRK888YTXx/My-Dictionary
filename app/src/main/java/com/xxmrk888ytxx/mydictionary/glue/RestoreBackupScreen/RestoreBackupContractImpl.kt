package com.xxmrk888ytxx.mydictionary.glue.RestoreBackupScreen

import android.net.Uri
import com.xxmrk888ytxx.mydictionary.UseCase.RestoreBackupUseCase.RestoreBackupUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.RestoreBackupUseCase.exception.BadBackupFileException
import com.xxmrk888ytxx.mydictionary.UseCase.RestoreBackupUseCase.exception.FileNotBackupFileException
import com.xxmrk888ytxx.mydictionary.presentation.Screen
import com.xxmrk888ytxx.restorebackupscreen.contract.RestoreBackupContract
import javax.inject.Inject

class RestoreBackupContractImpl @Inject constructor(
    private val restoreBackupUseCase: RestoreBackupUseCase
) : RestoreBackupContract {

    override suspend fun restore(backupFile: Uri): Result<Unit> {
        val result = restoreBackupUseCase.execute(backupFile)

        return if(result.isSuccess) {
            result
        } else {

            val moduleException:Throwable = when(val exception = result.exceptionOrNull()) {
                is FileNotBackupFileException -> com.xxmrk888ytxx.restorebackupscreen.exception.FileNotBackupFileException(exception.message)

                is BadBackupFileException -> com.xxmrk888ytxx.restorebackupscreen.exception.BadBackupFileException(exception.message)

                else -> exception ?: Exception()
            }

            Result.failure(moduleException)
        }
    }
}