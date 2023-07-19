package com.xxmrk888ytxx.coreandroid.ShareInterfaces

interface Navigator {

    fun toCreateWordGroupScreen()

    fun toViewGroupWordsScreen(wordGroupId:Int)

    fun toEditWordScreen(wordGroupId: Int,editWordId:Int = 0)

    fun backScreen()

    fun toWordTranslateTraining()

    fun toWordByEarTraining()
}