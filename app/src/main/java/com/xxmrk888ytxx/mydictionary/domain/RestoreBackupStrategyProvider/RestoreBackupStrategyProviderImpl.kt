package com.xxmrk888ytxx.mydictionary.domain.RestoreBackupStrategyProvider

import com.xxmrk888ytxx.mydictionary.domain.RestoreBackupStrategyProvider.strateges.RestoreStrategyForVersion_0
import com.xxmrk888ytxx.restorebackupscreen.exception.BadBackupFileException
import javax.inject.Inject
import javax.inject.Provider

class RestoreBackupStrategyProviderImpl @Inject constructor(
    private val restoreStrategyForVersion_0: Provider<RestoreStrategyForVersion_0>
) : RestoreBackupStrategyProvider {

    override suspend fun provideStrategy(versionBackup: Int): RestoreBackupStrategy {
        return when(versionBackup) {
            0 -> restoreStrategyForVersion_0.get()

            else -> throw BadBackupFileException("Version code not supported")
        }
    }
}