package com.xxmrk888ytxx.mydictionary.domain.AdsStateManager

import kotlinx.coroutines.flow.Flow

interface AdsStateManager {

    val isAdsEnabledFlow: Flow<Boolean>

    suspend fun disableAds()
}