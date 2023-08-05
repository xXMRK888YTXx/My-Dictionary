package com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupUseCase

import android.net.Uri
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordGroupModel

interface CreateBackupUseCase {

    suspend fun execute(
        backupWordGroups:Set<WordGroupModel>,
        fileUri:Uri
    ) : Result<Unit>
}