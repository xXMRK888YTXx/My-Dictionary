package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.createbackupscreen.contract.ProvideWordGroupsContract
import com.xxmrk888ytxx.mydictionary.glue.CreateBackupScreen.ProvideWordGroupsContractImpl
import dagger.Binds
import dagger.Module

@Module
interface CreateBackupScreenModule {

    @Binds
    fun bindCreateBackupScreen(
        provideWordGroupsContractImpl:ProvideWordGroupsContractImpl
    ) : ProvideWordGroupsContract
}