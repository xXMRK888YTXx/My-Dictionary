package com.xxmrk888ytxx.featureviewscreen.models

data class ScreenState(
    val currentScreenType:ScreenType = ScreenType.WELCOME,
    val isAgreeWithPrivacyPolicyAndTermsOfUse:Boolean = false
)
