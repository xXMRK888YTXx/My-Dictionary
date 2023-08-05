package com.xxmrk888ytxx.mydictionary.glue.ManageLanguageScreen

import android.database.sqlite.SQLiteConstraintException
import com.xxmrk888ytxx.managelanguagescreen.contract.RemoveLanguageContract
import com.xxmrk888ytxx.managelanguagescreen.exception.LanguageUsedException
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepository
import javax.inject.Inject

class RemoveLanguageContractImpl @Inject constructor(
    private val languageRepository: LanguageRepository
) : RemoveLanguageContract {


    override suspend fun removeLanguage(id: Int): Result<Unit> {
        return try {

            languageRepository.removeLanguage(id)

            return Result.success(Unit)
        }catch (e: SQLiteConstraintException) {
            return Result.failure(LanguageUsedException())
        }
    }
}