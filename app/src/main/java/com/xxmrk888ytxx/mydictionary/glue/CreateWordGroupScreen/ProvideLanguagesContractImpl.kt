package com.xxmrk888ytxx.mydictionary.glue.CreateWordGroupScreen

import com.xxmrk888ytxx.createwordgroupscreen.contract.ProvideLanguagesContract
import com.xxmrk888ytxx.createwordgroupscreen.models.Language
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//Temp Impl
class ProvideLanguagesContractImpl @Inject constructor(
    private val languageRepository: LanguageRepository
) : ProvideLanguagesContract {


    override val languages: Flow<ImmutableList<Language>> = languageRepository.languageFlow.map { list ->
        list.map { Language(it.id,it.name) }.toImmutableList()
    }
}