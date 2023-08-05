package com.xxmrk888ytxx.mydictionary.glue.SettingsScreen

import com.xxmrk888ytxx.mydictionary.domain.AdsStateManager.AdsStateManager
import com.xxmrk888ytxx.settingsscreen.contract.ProvideIsAdsEnabledInfoContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProvideIsAdsEnabledInfoContractImpl @Inject constructor(
    private val adsStateManager: AdsStateManager
) : ProvideIsAdsEnabledInfoContract {

    override val isAdsEnabled: Flow<Boolean> = adsStateManager.isAdsEnabledFlow
}