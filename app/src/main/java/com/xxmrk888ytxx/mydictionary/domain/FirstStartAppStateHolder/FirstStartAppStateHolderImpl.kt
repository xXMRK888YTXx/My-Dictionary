package com.xxmrk888ytxx.mydictionary.domain.FirstStartAppStateHolder

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.xxmrk888ytxx.preferencesstorage.PreferencesStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirstStartAppStateHolderImpl @Inject constructor(
    private val preferencesStorage: PreferencesStorage
) : FirstStartAppStateHolder {

    private val isFirstAppStartKey = booleanPreferencesKey("isFirstAppStartKey")


    override val isFirstAppStart: Flow<Boolean> = preferencesStorage.getProperty(isFirstAppStartKey,true)

    override suspend fun markAppStart() = withContext(Dispatchers.IO) {
        preferencesStorage.writeProperty(isFirstAppStartKey,false)
    }
}