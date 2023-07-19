package com.xxmrk888ytxx.wordbyeartrainingscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

sealed class LocalUiEvent : UiEvent {

    object StartTrainingEvent : LocalUiEvent()

    class NumberOfQuestionsChangedEvent(val newValue: String) : LocalUiEvent()

    class ChangeIsUsePhrasesEvent(val newValue: Boolean) : LocalUiEvent()

    class ChangeWordGroupSelectedStateEvent(val groupWordId: Int) : LocalUiEvent()

    class BackScreenEvent(val navigator: Navigator) : LocalUiEvent()
}
