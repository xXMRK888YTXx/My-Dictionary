package com.xxmrk888ytxx.featureviewscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.featureviewscreen.models.LocalUiEvent
import com.xxmrk888ytxx.featureviewscreen.models.ScreenState
import com.xxmrk888ytxx.featureviewscreen.models.ScreenType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class FeatureViewViewModel @Inject constructor() : ViewModel(), UiModel<ScreenState> {

    private val currentScreenTypeState:MutableStateFlow<ScreenType> = MutableStateFlow(ScreenType.WELCOME)

    private val isAgreeWithPrivacyPolicyAndTermsOfUseState = MutableStateFlow(false)

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return


    }

    override val state: Flow<ScreenState> = combine(
        currentScreenTypeState,
        isAgreeWithPrivacyPolicyAndTermsOfUseState
    ) { currentScreenType,isAgreeWithPrivacyPolicyAndTermsOfUse ->
        ScreenState(currentScreenType, isAgreeWithPrivacyPolicyAndTermsOfUse).also {
            cashedScreenState = it
        }
    }

    private var cashedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cashedScreenState
}