package com.xxmrk888ytxx.mydictionary.glue.SettingsScreen

import android.app.Activity
import com.xxmrk888ytxx.mydictionary.domain.BillingManager.BillingManager
import com.xxmrk888ytxx.settingsscreen.contract.BuyRemoveAdsContract
import javax.inject.Inject

class BuyRemoveAdsContractImpl @Inject constructor(
    private val billingManager: BillingManager
) : BuyRemoveAdsContract {

    override fun buy(activity: Activity) {
        billingManager.buyRemoveAds(activity)
    }
}