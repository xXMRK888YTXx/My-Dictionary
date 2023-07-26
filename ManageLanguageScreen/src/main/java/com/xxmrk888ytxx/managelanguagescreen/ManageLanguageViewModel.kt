package com.xxmrk888ytxx.managelanguagescreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.managelanguagescreen.models.ScreenState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ManageLanguageViewModel @Inject constructor(

) : ViewModel(),UiModel<ScreenState> {
    override fun handleEvent(event: UiEvent) {

    }

    override val state: Flow<ScreenState>
        get() = TODO("Not yet implemented")

    private var cashedScreenState = ScreenState()
    override val defValue: ScreenState
        get() = cashedScreenState

}