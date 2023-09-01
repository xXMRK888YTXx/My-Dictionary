package com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupFileUseCase

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordGroupModel
import java.io.File

interface CreateBackupFileUseCase {

    suspend fun execute(backupWordGroups: Set<WordGroupModel>,folderForBackup:File) : File
}