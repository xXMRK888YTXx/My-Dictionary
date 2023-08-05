package com.xxmrk888ytxx.mydictionary.glue.CreateBackupScreen

import com.xxmrk888ytxx.createbackupscreen.contract.ProvideWordGroupsContract
import com.xxmrk888ytxx.createbackupscreen.models.WordGroup
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProvideWordGroupsContractImpl @Inject constructor(
    private val wordGroupRepository: WordGroupRepository
) : ProvideWordGroupsContract {

    override val wordGroups: Flow<ImmutableList<WordGroup>> = wordGroupRepository.wordGroupsFlow.map { list ->
        list.map { WordGroup(it.id,it.name) }.toImmutableList()
    }
}