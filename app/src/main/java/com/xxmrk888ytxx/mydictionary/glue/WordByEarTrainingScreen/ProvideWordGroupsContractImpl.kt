package com.xxmrk888ytxx.mydictionary.glue.WordByEarTrainingScreen

import com.xxmrk888ytxx.basetrainingcomponents.models.WordGroup
import com.xxmrk888ytxx.mydictionary.UseCase.ProvideWordGroupsForTrainingUseCase.ProvideWordGroupsForTrainingUseCase
import com.xxmrk888ytxx.wordbyeartrainingscreen.contract.ProvideWordGroupsContract
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProvideWordGroupsContractImpl @Inject constructor(
    private val provideWordGroupsForTrainingUseCase: ProvideWordGroupsForTrainingUseCase
) : ProvideWordGroupsContract {

    override val wordGroups: Flow<ImmutableList<WordGroup>> = provideWordGroupsForTrainingUseCase.wordGroups
}