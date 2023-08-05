package com.xxmrk888ytxx.mydictionary.domain.BillingManager

import android.app.Activity

interface BillingManager {

    fun connectToGooglePlay()

    fun buyRemoveAds(activity: Activity)

    fun restorePurchases()
}