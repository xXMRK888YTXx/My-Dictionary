package com.xxmrk888ytxx.restorebackupscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.restorebackupscreen.models.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RestoreBackupViewModel @Inject constructor(

) : ViewModel(), UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {

    }

    override val state: Flow<ScreenState> = flowOf(ScreenState())

    private val cashedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cashedScreenState
}