package com.xxmrk888ytxx.wordgroupscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.androidcore.fastDebugLog
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.wordgroupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordgroupscreen.models.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class WordGroupViewModel @Inject constructor(

) : ViewModel(),UiModel<ScreenState> {


    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
           is LocalUiEvent.FloatButtonClickEvent,is LocalUiEvent.AddFirstWordGroupButtonClickEvent -> {
               val navigator = (event as? LocalUiEvent.FloatButtonClickEvent)?.navigator
                   ?: (event as? LocalUiEvent.AddFirstWordGroupButtonClickEvent)?.navigator ?: return

               navigator.toCreateWordGroupScreen()
           }



        }
    }

    private val _state:MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.EmptyWordGroupState)

    override val state: Flow<ScreenState> = _state.asStateFlow()


    override val defValue: ScreenState
        get() = ScreenState.EmptyWordGroupState


}