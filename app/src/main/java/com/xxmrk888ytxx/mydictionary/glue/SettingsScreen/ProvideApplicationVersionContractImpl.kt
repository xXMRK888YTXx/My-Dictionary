package com.xxmrk888ytxx.mydictionary.glue.SettingsScreen

import com.xxmrk888ytxx.mydictionary.domain.VersionProvider.VersionProvider
import com.xxmrk888ytxx.settingsscreen.contract.ProvideApplicationVersionContract
import javax.inject.Inject

class ProvideApplicationVersionContractImpl @Inject constructor(
    private val versionProvider: VersionProvider
) : ProvideApplicationVersionContract {


    override val applicationVersion: String = versionProvider.versionName
}