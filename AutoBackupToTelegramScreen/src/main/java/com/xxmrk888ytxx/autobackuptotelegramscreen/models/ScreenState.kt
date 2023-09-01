package com.xxmrk888ytxx.autobackuptotelegramscreen.models

data class ScreenState(
    val screenType: ScreenType = ScreenType.LOADING,
    val userIdText:String = "",
    val botKeyText:String = "",
    val isSaveTelegramDataAvailable:Boolean = false,
    val backupSettings: BackupSettings = BackupSettings()
)
