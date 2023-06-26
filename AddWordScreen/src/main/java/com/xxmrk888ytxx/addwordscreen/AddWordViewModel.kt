package com.xxmrk888ytxx.addwordscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.addwordscreen.models.LocalUiEvent
import com.xxmrk888ytxx.addwordscreen.models.ScreenState
import com.xxmrk888ytxx.androidcore.fastDebugLog
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AddWordViewModel @AssistedInject constructor(
    @Assisted private val wordGroupId:Int
) : ViewModel(),UiModel<ScreenState> {

    init {
        fastDebugLog(wordGroupId)
    }


    @AssistedFactory
    interface Factory {
        fun create(wordGroupId: Int) : AddWordViewModel
    }

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return
    }

    override val state: Flow<ScreenState> = flowOf(ScreenState())

    private var cachedScreenState:ScreenState = ScreenState()


    override val defValue: ScreenState
        get() = cachedScreenState
}