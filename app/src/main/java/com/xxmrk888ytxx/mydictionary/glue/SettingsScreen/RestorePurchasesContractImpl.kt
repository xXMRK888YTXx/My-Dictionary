package com.xxmrk888ytxx.mydictionary.glue.SettingsScreen

import com.xxmrk888ytxx.mydictionary.domain.BillingManager.BillingManager
import com.xxmrk888ytxx.settingsscreen.contract.RestorePurchasesContract
import javax.inject.Inject

class RestorePurchasesContractImpl @Inject constructor(
    private val billingManager: BillingManager
) : RestorePurchasesContract {

    override fun restore() {
        billingManager.restorePurchases()
    }
}