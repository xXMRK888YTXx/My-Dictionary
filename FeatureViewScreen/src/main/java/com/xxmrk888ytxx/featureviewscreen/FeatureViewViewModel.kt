@file:OptIn(ExperimentalFoundationApi::class)

package com.xxmrk888ytxx.featureviewscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.featureviewscreen.contract.OpenPrivacyPolicyContract
import com.xxmrk888ytxx.featureviewscreen.contract.OpenTermsOfUseContract
import com.xxmrk888ytxx.featureviewscreen.contract.ToApplicationContact
import com.xxmrk888ytxx.featureviewscreen.models.LocalUiEvent
import com.xxmrk888ytxx.featureviewscreen.models.ScreenState
import com.xxmrk888ytxx.featureviewscreen.models.ScreenType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeatureViewViewModel @Inject constructor(
    private val openPrivacyPolicyContract: OpenPrivacyPolicyContract,
    private val openTermsOfUseContract: OpenTermsOfUseContract,
    private val toApplicationContact: ToApplicationContact
) : ViewModel(), UiModel<ScreenState> {

    private val currentScreenTypeState:MutableStateFlow<ScreenType> = MutableStateFlow(ScreenType.WELCOME)

    private val isAgreeWithPrivacyPolicyState = MutableStateFlow(false)

    private val isAgreeWithTermsOfUseState = MutableStateFlow(false)

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            is LocalUiEvent.ToAgreeWithRulesScreenStateEvent -> {
                setupScreenState(ScreenType.AGREE_WITH_RULES,event.pagerState,event.uiScope)
            }

            is LocalUiEvent.ToMainScreenEvent -> {
                toApplicationContact.toApplication(event.navigator)
            }

            is LocalUiEvent.ToTrainingScreenStateEvent -> {
                setupScreenState(ScreenType.TRAINING,event.pagerState,event.uiScope)
            }

            is LocalUiEvent.ToWordScreenStateEvent -> {
                setupScreenState(ScreenType.WORD,event.pagerState,event.uiScope)
            }

            is LocalUiEvent.ToFreeScreenStateEvent -> {
                setupScreenState(ScreenType.FREE,event.pagerState,event.uiScope)
            }

            is LocalUiEvent.ChangeAgreeWithPrivacyPolicyState -> {
                isAgreeWithPrivacyPolicyState.update { event.newState }
            }

            is LocalUiEvent.ChangeAgreeWithTermsOfUseState -> {
                isAgreeWithTermsOfUseState.update { event.newState }
            }

            LocalUiEvent.OpenPrivacyPolicy -> {
                openPrivacyPolicyContract.openPrivacyPolicy()
            }

            LocalUiEvent.OpenTermsOfUse -> {
                openTermsOfUseContract.openTermsOfUse()
            }

            is LocalUiEvent.SkipEvent -> {
                val lastPage = ScreenType.values().size

                currentScreenTypeState.update { ScreenType.AGREE_WITH_RULES }

                event.uiScope.launch {
                    event.pager.animateScrollToPage(lastPage)
                }
            }
        }

    }

    private fun setupScreenState(
        screenType:ScreenType,
        pagerState: PagerState,
        uiScope:CoroutineScope
    ) {
        currentScreenTypeState.update { screenType }

        uiScope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    override val state: Flow<ScreenState> = combine(
        currentScreenTypeState,
        isAgreeWithPrivacyPolicyState,
        isAgreeWithTermsOfUseState
    ) { currentScreenType,isAgreeWithPrivacyPolicy,isAgreeWithTermsOfUse ->
        ScreenState(currentScreenType, isAgreeWithPrivacyPolicy,isAgreeWithTermsOfUse).also {
            cashedScreenState = it
        }
    }

    private var cashedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cashedScreenState
}