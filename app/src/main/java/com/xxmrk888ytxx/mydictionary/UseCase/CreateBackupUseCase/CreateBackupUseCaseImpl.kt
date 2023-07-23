package com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupUseCase

import android.net.Uri
import javax.inject.Inject

class CreateBackupUseCaseImpl @Inject constructor(

) : CreateBackupUseCase {

    override suspend fun execute(backupWordGroupsId: Set<Int>, fileUri: Uri): Result<Unit> {
        return Result.failure(Exception())
    }
}