package com.xxmrk888ytxx.mydictionary.glue.ManageLanguageScreen

import com.xxmrk888ytxx.managelanguagescreen.contract.ProvideLanguageContract
import com.xxmrk888ytxx.managelanguagescreen.models.Language
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.LanguageModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProvideLanguageContractImpl @Inject constructor(
    private val languageRepository: LanguageRepository
) : ProvideLanguageContract {

    override val languages: Flow<ImmutableList<Language>> = languageRepository.languageFlow.map { list ->
        list.map { it.toModuleModel() }.toImmutableList()
    }

    private fun LanguageModel.toModuleModel() : Language {
        return Language(id, name)
    }
}