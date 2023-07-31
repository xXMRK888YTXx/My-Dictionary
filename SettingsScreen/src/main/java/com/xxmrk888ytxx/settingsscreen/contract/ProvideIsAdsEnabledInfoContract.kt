package com.xxmrk888ytxx.settingsscreen.contract

import kotlinx.coroutines.flow.Flow

interface ProvideIsAdsEnabledInfoContract {

    val isAdsEnabled: Flow<Boolean>
}