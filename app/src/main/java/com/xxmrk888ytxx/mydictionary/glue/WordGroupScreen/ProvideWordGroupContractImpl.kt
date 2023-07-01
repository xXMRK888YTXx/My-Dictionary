package com.xxmrk888ytxx.mydictionary.glue.WordGroupScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.LanguageModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordGroupModel
import com.xxmrk888ytxx.wordgroupscreen.contract.ProvideWordGroupContract
import com.xxmrk888ytxx.wordgroupscreen.models.Language
import com.xxmrk888ytxx.wordgroupscreen.models.WordGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProvideWordGroupContractImpl @Inject constructor(
    private val wordGroupRepository: WordGroupRepository,
    private val languageRepository: LanguageRepository
) : ProvideWordGroupContract {

    override val wordsGroup: Flow<List<WordGroup>> = wordGroupRepository
        .wordGroupsFlow
        .combine(languageRepository.languageFlow.toMap()) { wordsGroupList,languageMap ->
            wordsGroupList.map {
                WordGroup(
                    it.id,
                    it.name,
                    primaryLanguage =  languageMap[it.primaryLanguageId]!!.toModuleLanguage(),
                    secondaryLanguage = languageMap[it.secondaryLanguageId]!!.toModuleLanguage(),
                    imageUrl = it.imageUrl
                )

            }
        }

    private fun LanguageModel.toModuleLanguage() : Language {
        return Language(id,name)
    }

    private fun Flow<List<LanguageModel>>.toMap(): Flow<Map<Int, LanguageModel>> {
        return map {
            val map = mutableMapOf<Int, LanguageModel>()

            it.forEach { entity ->
                map[entity.id] = entity
            }

            map
        }
    }
}