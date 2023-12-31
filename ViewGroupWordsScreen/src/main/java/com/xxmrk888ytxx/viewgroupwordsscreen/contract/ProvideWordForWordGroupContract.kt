package com.xxmrk888ytxx.viewgroupwordsscreen.contract

import com.xxmrk888ytxx.viewgroupwordsscreen.models.Word
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface ProvideWordForWordGroupContract {

    fun getWords(wordGroupId:Int) : Flow<ImmutableList<Word>>
}