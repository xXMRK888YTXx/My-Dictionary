package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.CheckTelegramDataContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.CreateBackupContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.ManageBackupSettingsContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.OpenWhereToGetInstructionsContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.ProvideBackupSettingsContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.RemoveTelegramDataContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.SaveTelegramDataContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.ValidateTelegramDataContract
import com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen.CheckTelegramDataContractImpl
import com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen.CreateBackupContractImpl
import com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen.ManageBackupSettingsContractImpl
import com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen.OpenWhereToGetInstructionsContractImpl
import com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen.ProvideBackupSettingsContractImpl
import com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen.RemoveTelegramDataContractImpl
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

    @Binds
    fun bindManageBackupSettingsContract(
        manageBackupSettingsContractImpl: ManageBackupSettingsContractImpl
    ) : ManageBackupSettingsContract

    @Binds
    fun bindProvideBackupSettingsContract(
        provideBackupSettingsContractImpl: ProvideBackupSettingsContractImpl
    ) : ProvideBackupSettingsContract

    @Binds
    fun bindOpenWhereToGetInstructionsContract(
        openWhereToGetInstructionsContract: OpenWhereToGetInstructionsContractImpl
    ) : OpenWhereToGetInstructionsContract

    @Binds
    fun bindCreateBackupContract(
        createBackupContractImpl: CreateBackupContractImpl
    ) : CreateBackupContract

    @Binds
    fun bindRemoveTelegramDataContract(
        removeTelegramDataContractImpl: RemoveTelegramDataContractImpl
    ) : RemoveTelegramDataContract
}