package com.xxmrk888ytxx.mydictionary.domain.RestoreBackupStrategyProvider

interface RestoreBackupStrategyProvider {

    suspend fun provideStrategy(versionBackup:Int) : RestoreBackupStrategy
}