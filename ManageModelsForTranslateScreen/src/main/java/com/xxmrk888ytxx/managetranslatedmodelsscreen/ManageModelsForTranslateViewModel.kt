package com.xxmrk888ytxx.managetranslatedmodelsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.managetranslatedmodelsscreen.contracts.ProvideTranslateModelsContract
import com.xxmrk888ytxx.managetranslatedmodelsscreen.contracts.RemoveTranslationModelContract
import com.xxmrk888ytxx.managetranslatedmodelsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.managetranslatedmodelsscreen.models.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ManageModelsForTranslateViewModel @Inject constructor(
    private val provideTranslateModelsContract: ProvideTranslateModelsContract,
    private val removeTranslationModelContract: RemoveTranslationModelContract
) : ViewModel(),UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            is LocalUiEvent.BackScreenEvent -> {
                event.navigator.backScreen()
            }

            is LocalUiEvent.RemoveTranslateModel -> {
                viewModelScope.launch {

                    isDeletingState.update { true }

                    removeTranslationModelContract.removeModel(event.code).first()
                        .onSuccess {
                            event.uiScope.launch {
                                event.snackbarHostState.showSnackbar(event.context.getString(R.string.model_has_been_deleted))
                            }
                        }
                        .onFailure {
                            event.uiScope.launch {
                                event.snackbarHostState.showSnackbar(event.context.getString(R.string.couldn_t_delete_the_model))
                            }
                        }

                    isDeletingState.update { false }
                }
            }
        }
    }

    private val isDeletingState = MutableStateFlow(false)

    override val state: Flow<ScreenState> = combine(
        provideTranslateModelsContract.models,
        isDeletingState
    ) { models,isLoading ->
        ScreenState(models,isLoading).also {
            cachedScreenState = it
        }
    }

    private var cachedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cachedScreenState


}