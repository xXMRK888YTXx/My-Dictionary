package com.xxmrk888ytxx.wordgroupscreen.contract

import com.xxmrk888ytxx.wordgroupscreen.models.WordGroup
import kotlinx.coroutines.flow.Flow

interface ProvideWordGroupContract {

    val wordsGroup:Flow<List<WordGroup>>
}