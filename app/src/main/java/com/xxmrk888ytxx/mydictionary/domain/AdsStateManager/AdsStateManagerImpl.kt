package com.xxmrk888ytxx.mydictionary.domain.AdsStateManager

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.xxmrk888ytxx.preferencesstorage.PreferencesStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdsStateManagerImpl @Inject constructor(
    private val preferencesStorage: PreferencesStorage
) : AdsStateManager {

    private val disableAdsKey = booleanPreferencesKey("disableAdsKey")

    override val isAdsEnabledFlow: Flow<Boolean> = preferencesStorage.getProperty(disableAdsKey,true)


    override suspend fun disableAds() {
        preferencesStorage.writeProperty(disableAdsKey,false)
    }
}