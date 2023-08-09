package com.xxmrk888ytxx.autobackuptotelegramscreen.models

data class ScreenState(
    val screenType: ScreenType = ScreenType.LOADING,
    val userIdText:String = "",
    val botKeyText:String = "",
    val oneLineDataText:String = "",
    val isSaveTelegramDataAvailable:Boolean = false,
    val isSaveOneLineTelegramDataAvailable:Boolean = false
)
