package com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.LanguageModel
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordGroupInfoContract
import com.xxmrk888ytxx.viewgroupwordsscreen.models.WordGroupInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProvideWordGroupInfoContractImpl @Inject constructor(
    private val wordGroupRepository: WordGroupRepository,
    private val languageRepository: LanguageRepository
) : ProvideWordGroupInfoContract {

    override fun getWordGroupInfo(wordGroupId: Int): Flow<WordGroupInfo> {
        return wordGroupRepository
            .getWordGroupById(wordGroupId)
            .combine(languageRepository.languageFlow.toMap()) { wordGroup,languageMap ->
                WordGroupInfo(
                    wordGroup.name,
                    languageMap[wordGroup.primaryLanguageId]!!.name,
                    languageMap[wordGroup.secondaryLanguageId]!!.name
                )
            }

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