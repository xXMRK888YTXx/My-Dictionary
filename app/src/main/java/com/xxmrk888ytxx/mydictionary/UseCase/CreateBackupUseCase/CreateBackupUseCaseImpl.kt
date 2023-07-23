package com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupUseCase

import android.net.Uri
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordGroupModel
import javax.inject.Inject

class CreateBackupUseCaseImpl @Inject constructor(

) : CreateBackupUseCase {

    override suspend fun execute(backupWordGroupsId: Set<WordGroupModel>, fileUri: Uri): Result<Unit> {
        return Result.failure(Exception())
    }
}