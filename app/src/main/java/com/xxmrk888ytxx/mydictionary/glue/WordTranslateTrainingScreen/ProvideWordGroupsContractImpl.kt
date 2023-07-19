package com.xxmrk888ytxx.mydictionary.glue.WordTranslateTrainingScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import com.xxmrk888ytxx.wordtranslatetrainingscreen.contracts.ProvideWordGroupsContract
import com.xxmrk888ytxx.basetrainingcomponents.models.WordGroup
import com.xxmrk888ytxx.mydictionary.UseCase.ProvideWordGroupsForTrainingUseCase.ProvideWordGroupsForTrainingUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProvideWordGroupsContractImpl @Inject constructor(
    private val provideWordGroupsForTrainingUseCase: ProvideWordGroupsForTrainingUseCase
) : ProvideWordGroupsContract {

    override val wordGroups: Flow<ImmutableList<WordGroup>> = provideWordGroupsForTrainingUseCase.wordGroups

}