package com.xxmrk888ytxx.settingsscreen

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.settingsscreen.contract.BuyRemoveAdsContract
import com.xxmrk888ytxx.settingsscreen.contract.OpenEmailClientForWriteDeveloperContract
import com.xxmrk888ytxx.settingsscreen.contract.OpenPrivacyPolicyContract
import com.xxmrk888ytxx.settingsscreen.contract.OpenSourceCodeContract
import com.xxmrk888ytxx.settingsscreen.contract.OpenTermsOfUseContract
import com.xxmrk888ytxx.settingsscreen.contract.ProvideApplicationVersionContract
import com.xxmrk888ytxx.settingsscreen.contract.ProvideIsAdsEnabledInfoContract
import com.xxmrk888ytxx.settingsscreen.contract.RestorePurchasesContract
import com.xxmrk888ytxx.settingsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.settingsscreen.models.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val provideApplicationVersionContract: ProvideApplicationVersionContract,
    private val openPrivacyPolicyContract: OpenPrivacyPolicyContract,
    private val openTermsOfUseContract: OpenTermsOfUseContract,
    private val openSourceCodeContract: OpenSourceCodeContract,
    private val openEmailClientForWriteDeveloperContract: OpenEmailClientForWriteDeveloperContract,
    private val buyRemoveAdsContract: BuyRemoveAdsContract,
    private val restorePurchasesContract: RestorePurchasesContract,
    private val provideIsAdsEnabledInfoContract: ProvideIsAdsEnabledInfoContract
) : ViewModel(),UiModel<ScreenState> {
    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            LocalUiEvent.OpenPrivacyPolicy -> {
                openPrivacyPolicyContract.open()
            }

            LocalUiEvent.OpenSourceCodeEvent -> {
                openSourceCodeContract.open()
            }

            LocalUiEvent.OpenTermsOfUse -> {
                openTermsOfUseContract.open()
            }

            LocalUiEvent.WriteToDeveloperEvent -> {
                openEmailClientForWriteDeveloperContract.open()
            }

            is LocalUiEvent.OpenCreateBackupScreenEvent -> {
                event.navigator.toCreateBackupScreen()
            }

            is LocalUiEvent.OpenRestoreBackupScreenEvent -> {
                event.navigator.toRestoreBackupScreen()
            }

            is LocalUiEvent.OpenLanguageManageScreen -> {
                event.navigator.toLanguageManageScreen()
            }

            is LocalUiEvent.RequestBuyRemoveAdsEvent -> {
                val activity = event.context as? Activity ?: return

                buyRemoveAdsContract.buy(activity)
            }

            is LocalUiEvent.RestorePurchasesEvent -> {
                restorePurchasesContract.restore()
            }
        }
    }

    override val state: Flow<ScreenState> = provideIsAdsEnabledInfoContract.isAdsEnabled.map {
        ScreenState(
            applicationVersion = provideApplicationVersionContract.applicationVersion,
            isAdsEnabled = it
        ).also { cashedScreenState = it }
    }


    private var cashedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cashedScreenState

}