package com.xxmrk888ytxx.mydictionary.glue.CreateWordGroupScreen

import com.xxmrk888ytxx.mydictionary.data.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.models.LanguageModel
import com.xxmrk888ytxx.mydictionary.models.WordGroupModel
import com.xxmrk888ytxx.wordgroupscreen.contract.ProvideWordGroupContract
import com.xxmrk888ytxx.wordgroupscreen.models.Language
import com.xxmrk888ytxx.wordgroupscreen.models.WordGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProvideWordGroupContractImpl @Inject constructor(
    private val wordGroupRepository: WordGroupRepository
) : ProvideWordGroupContract {

    override val wordsGroup: Flow<List<WordGroup>> = wordGroupRepository.wordGroupsFlow.map { list ->
        list.map { it.toModuleWordGroup() }
    }

    private fun WordGroupModel.toModuleWordGroup() : WordGroup {
        return WordGroup(id,name,primaryLanguage.toModuleLanguage(),secondaryLanguage.toModuleLanguage(),imageUrl)
    }

    private fun LanguageModel.toModuleLanguage() : Language {
        return Language(id,name)
    }
}