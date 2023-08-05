package com.xxmrk888ytxx.trainingactionsscreen.models

import AdController
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

internal sealed class LocalUiEvent : UiEvent {

    class OpenWordTranslateTraining(val navigator: Navigator, val adController: AdController) : LocalUiEvent()

    class OpenWordsByEarTraining(val navigator: Navigator, val adController: AdController) : LocalUiEvent()
}
