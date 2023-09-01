package com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder

enum class BackupTime(val id:Int) {
    NONE(-1),
    HOURS_6(0),
    HOURS_12(1),
    DAY_1(2),
    WEEK_1(3)
}