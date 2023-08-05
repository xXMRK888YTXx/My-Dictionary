package com.xxmrk888ytxx.wordgroupscreen.contract

import com.xxmrk888ytxx.wordgroupscreen.models.WordGroup

interface RemoveWordGroupContract {

    suspend fun removeWordGroup(wordGroupId:Int)
}