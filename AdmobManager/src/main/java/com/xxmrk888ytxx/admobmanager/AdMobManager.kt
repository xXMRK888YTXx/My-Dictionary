package com.xxmrk888ytxx.admobmanager

import android.app.Activity
import android.content.Context

interface AdMobManager {

    fun initAdmob()

    fun showInterstitialAd(key:String,activity: Activity)


    companion object {

        fun create(context:Context) : AdMobManager = AdMobManagerImpl(context)
    }
}