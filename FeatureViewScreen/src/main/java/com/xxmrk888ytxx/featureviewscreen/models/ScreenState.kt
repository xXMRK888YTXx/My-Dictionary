package com.xxmrk888ytxx.featureviewscreen.models

data class ScreenState(
    val currentScreenType:ScreenType = ScreenType.WELCOME,
    val isAgreeWithPrivacyPolicy:Boolean = false,
    val isAgreeWithTermsOfUse:Boolean = false,
)
