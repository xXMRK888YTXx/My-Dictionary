package com.xxmrk888ytxx.coreandroid

/**
 * [Ru]
 * Интрефейс для показа межстраничной рекламы
 */

/**
 * [En]
 * Interface for view interstitial ads
 */
interface AdInterstitialController {

    fun showMainScreenToTrainingScreenBanner()

    fun showWordGroupScreenToViewWordOfWordGroup()

    fun showWordGroupScreenToCreateWordGroupScreen()

    fun showTranslatorScreenAd()
}