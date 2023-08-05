package com.xxmrk888ytxx.mydictionary.glue.FeatureViewScreen

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import com.xxmrk888ytxx.featureviewscreen.contract.ToApplicationContact
import com.xxmrk888ytxx.mydictionary.DI.Qualifiers.ApplicationScopeQualifier
import com.xxmrk888ytxx.mydictionary.domain.FirstStartAppStateHolder.FirstStartAppStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ToApplicationContactImpl @Inject constructor(
    @ApplicationScopeQualifier private val applicationScope:CoroutineScope,
    private val firstStartAppStateHolder: FirstStartAppStateHolder
) : ToApplicationContact {

    override fun toApplication(navigator: Navigator) {
        navigator.toMainScreen()

        applicationScope.launch {
            firstStartAppStateHolder.markAppStart()
        }
    }
}