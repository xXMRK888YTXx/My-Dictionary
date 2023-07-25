package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.glue.RestoreBackupScreen.RestoreBackupContractImpl
import com.xxmrk888ytxx.restorebackupscreen.contract.RestoreBackupContract
import dagger.Binds
import dagger.Module

@Module
interface RestoreBackupScreenModule {

    @Binds
    fun bindRestoreBackupContract(
        RestoreBackupContractImpl: RestoreBackupContractImpl
    ) : RestoreBackupContract
}