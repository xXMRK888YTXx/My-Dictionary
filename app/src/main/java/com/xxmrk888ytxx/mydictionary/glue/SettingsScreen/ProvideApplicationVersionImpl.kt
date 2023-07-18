package com.xxmrk888ytxx.mydictionary.glue.SettingsScreen

import com.xxmrk888ytxx.mydictionary.domain.VersionProvider.VersionProvider
import com.xxmrk888ytxx.settingsscreen.contract.ProvideApplicationVersion
import javax.inject.Inject

class ProvideApplicationVersionImpl @Inject constructor(
    private val versionProvider: VersionProvider
) : ProvideApplicationVersion {


    override val applicationVersion: String = versionProvider.versionName
}