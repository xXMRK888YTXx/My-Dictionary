package com.xxmrk888ytxx.trainingactionsscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEventHandler
import com.xxmrk888ytxx.trainingactionsscreen.models.LocalUiEvent
import javax.inject.Inject

class TrainingActionViewModel @Inject constructor(

) : ViewModel(),UiEventHandler {

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return
    }


}