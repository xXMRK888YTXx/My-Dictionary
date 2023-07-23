package com.xxmrk888ytxx.mydictionary.glue.CreateBackupScreen

import android.net.Uri
import com.xxmrk888ytxx.createbackupscreen.contract.CreateBackupContract
import com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupUseCase.CreateBackupUseCase
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordGroupModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CreateBackupContractImpl @Inject constructor(
    private val createBackupUseCase: CreateBackupUseCase,
    private val wordGroupsRepository: WordGroupRepository
) : CreateBackupContract {


    override suspend fun createBackup(
        selectedWordGroupsId: Set<Int>,
        backupFileUri: Uri,
    ): Result<Unit> {
        val finalSet = mutableSetOf<WordGroupModel>()

        selectedWordGroupsId.forEach {
            finalSet.add(wordGroupsRepository.getWordGroupById(it).first())
        }

        return createBackupUseCase.execute(finalSet, backupFileUri)
    }
}