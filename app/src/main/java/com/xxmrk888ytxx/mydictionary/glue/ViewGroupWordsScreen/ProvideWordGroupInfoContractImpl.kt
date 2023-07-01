package com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordGroupModel
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordGroupInfoContract
import com.xxmrk888ytxx.viewgroupwordsscreen.models.WordGroupInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProvideWordGroupInfoContractImpl @Inject constructor(
    private val wordGroupRepository: WordGroupRepository
) : ProvideWordGroupInfoContract {

    override fun getWordGroupInfo(wordGroupId: Int): Flow<WordGroupInfo> {
        return wordGroupRepository.getWordGroupById(wordGroupId).map {
            it.toModuleModel()
        }
    }

    private fun WordGroupModel.toModuleModel() : WordGroupInfo {
        return WordGroupInfo(name,primaryLanguage.name,secondaryLanguage.name)
    }
}