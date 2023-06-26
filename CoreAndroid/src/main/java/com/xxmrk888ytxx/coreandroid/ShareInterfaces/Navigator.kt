package com.xxmrk888ytxx.coreandroid.ShareInterfaces

interface Navigator {

    fun toCreateWordGroupScreen()

    fun toViewGroupWordsScreen(wordGroupId:Int)

    fun toAddWordScreen(wordGroupId: Int)

    fun backScreen()
}