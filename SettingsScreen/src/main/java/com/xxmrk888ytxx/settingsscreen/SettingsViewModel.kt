package com.xxmrk888ytxx.settingsscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.settingsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.settingsscreen.models.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SettingsViewModel @Inject constructor(

) : ViewModel(),UiModel<ScreenState> {
    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return
    }

    override val state: Flow<ScreenState> = flowOf(ScreenState())

    private var cashedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cashedScreenState

}