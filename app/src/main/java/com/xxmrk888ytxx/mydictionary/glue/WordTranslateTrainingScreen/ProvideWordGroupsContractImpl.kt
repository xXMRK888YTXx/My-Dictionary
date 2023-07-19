package com.xxmrk888ytxx.mydictionary.glue.WordTranslateTrainingScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import com.xxmrk888ytxx.wordtranslatetrainingscreen.contracts.ProvideWordGroupsContract
import com.xxmrk888ytxx.basetrainingcomponents.models.WordGroup
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProvideWordGroupsContractImpl @Inject constructor(
    private val wordGroupRepository: WordGroupRepository,
    private val wordRepository: WordRepository
) : ProvideWordGroupsContract {
    override val wordGroups: Flow<ImmutableList<WordGroup>> = combine(
        wordRepository.getWords().toMap(),
        wordGroupRepository.wordGroupsFlow
    ) { wordMap,wordGroupsList ->
        val finalList = mutableListOf<WordGroup>()

        wordGroupsList.forEach { model ->
            wordMap[model.id]?.let {
                if(it.size >= 5) {
                    finalList.add(WordGroup(model.id,model.name))
                }
            }
        }

        finalList.toImmutableList()
    }


    private fun Flow<List<WordModel>>.toMap() : Flow<Map<Int,List<WordModel>>> {
        return map { list ->
            val finalMap = mutableMapOf<Int,MutableList<WordModel>>()

            list.forEach { wordModel ->
                if(finalMap.containsKey(wordModel.wordGroupId)) {
                    finalMap[wordModel.wordGroupId]!!.add(wordModel)
                } else {
                    finalMap[wordModel.wordGroupId] = mutableListOf(wordModel)
                }
            }

            finalMap
        }
    }

}