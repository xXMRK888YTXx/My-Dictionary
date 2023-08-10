package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.CheckTelegramDataContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.SaveTelegramDataContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.ValidateTelegramDataContract
import com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen.CheckTelegramDataContractImpl
import com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen.SaveTelegramDataContractImpl
import com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen.ValidateTelegramDataContractImpl
import dagger.Binds
import dagger.Module

@Module
interface AutoBackupToTelegramScreenModule {

    @Binds
    fun bindValidateTelegramDataContract(
        validateTelegramDataContractImpl:ValidateTelegramDataContractImpl
    ) : ValidateTelegramDataContract

    @Binds
    fun bindSaveTelegramDataContract(
        saveTelegramDataContractImpl: SaveTelegramDataContractImpl
    ) : SaveTelegramDataContract

    @Binds
    fun bindCheckTelegramDataContract(
        checkTelegramDataContractImpl: CheckTelegramDataContractImpl
    ) : CheckTelegramDataContract
}