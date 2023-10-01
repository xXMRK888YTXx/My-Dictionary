package com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.translatorscreen.contract.ProvideWordGroupInfo
import com.xxmrk888ytxx.translatorscreen.models.WordGroup
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProvideWordGroupInfoImpl @Inject constructor(
    private val wordGroupRepository: WordGroupRepository
) : ProvideWordGroupInfo {

    override val wordGroups: Flow<ImmutableList<WordGroup>> = wordGroupRepository.wordGroupsFlow.map { list ->
        list.map { WordGroup(it.id,it.name) }.toImmutableList()
    }
}