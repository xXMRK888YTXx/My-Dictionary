package com.xxmrk888ytxx.managetranslatedmodelsscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.managetranslatedmodelsscreen.contracts.ProvideTranslateModelsContract
import com.xxmrk888ytxx.managetranslatedmodelsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.managetranslatedmodelsscreen.models.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ManageModelsForTranslateViewModel @Inject constructor(
    private val provideTranslateModelsContract: ProvideTranslateModelsContract
) : ViewModel(),UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return
    }

    private val isLoadingState = MutableStateFlow(true)

    override val state: Flow<ScreenState> = combine(
        provideTranslateModelsContract.models,
        isLoadingState
    ) { models,isLoading ->
        ScreenState(models,isLoading).also {
            cachedScreenState = it
        }
    }

    private var cachedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cachedScreenState


}