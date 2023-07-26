package com.xxmrk888ytxx.mydictionary.glue.ManageLanguageScreen

import com.xxmrk888ytxx.managelanguagescreen.contract.CreateNewLanguageContract
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.LanguageModel
import javax.inject.Inject

class CreateNewLanguageContractImpl @Inject constructor(
    private val languageRepository: LanguageRepository
) : CreateNewLanguageContract {

    override suspend fun createNewLanguage(newLanguageName: String) {
        languageRepository.insertLanguage(LanguageModel(0,newLanguageName))
    }
}