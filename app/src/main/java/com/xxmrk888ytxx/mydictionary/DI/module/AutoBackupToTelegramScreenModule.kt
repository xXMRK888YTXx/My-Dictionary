package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.CheckTelegramDataContract
import com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen.CheckTelegramDataContractImpl
import dagger.Binds
import dagger.Module

@Module
interface AutoBackupToTelegramScreenModule {

    @Binds
    fun bindCheckTelegramDataContract(
        checkTelegramDataContractImpl:CheckTelegramDataContractImpl
    ) : CheckTelegramDataContract
}