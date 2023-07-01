package com.xxmrk888ytxx.viewgroupwordsscreen.contract

import com.xxmrk888ytxx.viewgroupwordsscreen.models.WordGroupInfo
import kotlinx.coroutines.flow.Flow

interface ProvideWordGroupInfoContract {

    fun getWordGroupInfo(wordGroupId:Int) : Flow<WordGroupInfo>
}