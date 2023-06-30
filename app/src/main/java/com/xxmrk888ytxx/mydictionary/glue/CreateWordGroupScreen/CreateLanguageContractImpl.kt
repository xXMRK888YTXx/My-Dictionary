package com.xxmrk888ytxx.mydictionary.glue.CreateWordGroupScreen

import com.xxmrk888ytxx.createwordgroupscreen.contract.CreateLanguageContract
import com.xxmrk888ytxx.createwordgroupscreen.models.Language
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepository
import com.xxmrk888ytxx.mydictionary.models.LanguageModel
import javax.inject.Inject

class CreateLanguageContractImpl @Inject constructor(
    private val languageRepository: LanguageRepository
) : CreateLanguageContract {

    override suspend fun newLanguage(newLanguageName:String) {
        languageRepository.insertLanguage(LanguageModel(0,newLanguageName))
    }
}