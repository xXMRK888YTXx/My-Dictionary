package com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen

import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordForWordGroupContract
import com.xxmrk888ytxx.viewgroupwordsscreen.models.Word
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ProvideWordForWordGroupContractImpl @Inject constructor(

) : ProvideWordForWordGroupContract {

    override val words: Flow<ImmutableList<Word>> = flowOf(persistentListOf())
}