package com.xxmrk888ytxx.settingsscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.settingsscreen.contract.OpenEmailClientForWriteDeveloperContract
import com.xxmrk888ytxx.settingsscreen.contract.OpenPrivacyPolicyContract
import com.xxmrk888ytxx.settingsscreen.contract.OpenSourceCodeContract
import com.xxmrk888ytxx.settingsscreen.contract.OpenTermsOfUseContract
import com.xxmrk888ytxx.settingsscreen.contract.ProvideApplicationVersion
import com.xxmrk888ytxx.settingsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.settingsscreen.models.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val provideApplicationVersion: ProvideApplicationVersion,
    private val openPrivacyPolicyContract: OpenPrivacyPolicyContract,
    private val openTermsOfUseContract: OpenTermsOfUseContract,
    private val openSourceCodeContract: OpenSourceCodeContract,
    private val openEmailClientForWriteDeveloperContract: OpenEmailClientForWriteDeveloperContract
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
        }
    }

    override val state: Flow<ScreenState> = flowOf(ScreenState(provideApplicationVersion.applicationVersion))

    private var cashedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cashedScreenState

}