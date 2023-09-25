package com.xxmrk888ytxx.translatorscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.translatorscreen.models.LocalUiEvent
import com.xxmrk888ytxx.translatorscreen.models.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class TranslatorViewModel @Inject constructor(

) : ViewModel(),UiModel<ScreenState> {
    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            else -> {}
        }
    }

    override val state: Flow<ScreenState> = flowOf(ScreenState())

    private var cashedState = ScreenState()

    override val defValue: ScreenState
        get() = cashedState
}