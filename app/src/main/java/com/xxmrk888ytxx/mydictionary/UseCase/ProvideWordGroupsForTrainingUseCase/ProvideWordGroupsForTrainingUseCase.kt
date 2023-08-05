package com.xxmrk888ytxx.mydictionary.UseCase.ProvideWordGroupsForTrainingUseCase

import com.xxmrk888ytxx.basetrainingcomponents.models.WordGroup
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface ProvideWordGroupsForTrainingUseCase {

    val wordGroups: Flow<ImmutableList<WordGroup>>
}