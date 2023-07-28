package com.xxmrk888ytxx.mydictionary.glue.FeatureViewScreen

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import com.xxmrk888ytxx.featureviewscreen.contract.ToApplicationContact
import javax.inject.Inject

class ToApplicationContactImpl @Inject constructor(

) : ToApplicationContact {

    override fun toApplication(navigator: Navigator) {
        navigator.toMainScreen()
    }
}