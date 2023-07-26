package com.xxmrk888ytxx.managelanguagescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.managelanguagescreen.contract.CreateNewLanguageContract
import com.xxmrk888ytxx.managelanguagescreen.contract.ProvideLanguageContract
import com.xxmrk888ytxx.managelanguagescreen.contract.RemoveLanguageContract
import com.xxmrk888ytxx.managelanguagescreen.models.CreateLanguageDialogState
import com.xxmrk888ytxx.managelanguagescreen.models.LocalUiEvent
import com.xxmrk888ytxx.managelanguagescreen.models.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ManageLanguageViewModel @Inject constructor(
    private val provideLanguageContract: ProvideLanguageContract,
    private val removeLanguageContract: RemoveLanguageContract,
    private val createNewLanguageContract: CreateNewLanguageContract
) : ViewModel(), UiModel<ScreenState> {

    private val createLanguageDialogState: MutableStateFlow<CreateLanguageDialogState> =
        MutableStateFlow(CreateLanguageDialogState.Hidden)

    private val isRemovingInProcessState = MutableStateFlow(false)

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            is LocalUiEvent.RemoveLanguageEvent -> {
                if(isRemovingInProcessState.value) return

                isRemovingInProcessState.update { true }

                viewModelScope.launch(Dispatchers.IO) {

                    val isSuccess = removeLanguageContract.removeLanguage(event.id).isSuccess

                    event.uiScope.launch {
                        val text = if(isSuccess)
                            "Language successfully deleted"
                        else
                            "The language cannot be deleted. It is used in another group of words"

                        event.snackbarHostState.showSnackbar(text)
                    }


                    isRemovingInProcessState.update { false }
                }
            }

            is LocalUiEvent.BackScreenEvent -> {
                event.navigator.backScreen()
            }

            LocalUiEvent.HideCreateNewLanguageDialog -> {
                createLanguageDialogState.update { CreateLanguageDialogState.Hidden }
            }

            LocalUiEvent.ShowCreateNewLanguageDialog -> {
                createLanguageDialogState.update { CreateLanguageDialogState.Showed() }
            }

            is LocalUiEvent.CreateNewLanguage -> {
                val dialogState = (createLanguageDialogState.value as? CreateLanguageDialogState.Showed) ?: return

                if(dialogState.isAddingInProcess) return

                createLanguageDialogState.update { dialogState.copy(isAddingInProcess = true) }


                viewModelScope.launch(Dispatchers.IO) {
                    createNewLanguageContract.createNewLanguage(dialogState.languageName)

                    createLanguageDialogState.update { CreateLanguageDialogState.Hidden }

                    event.scope.launch {
                        event.snackbarHostState.showSnackbar("Language created")
                    }
                }
            }

            is LocalUiEvent.NewLanguageNameEnteredEvent -> {
                createLanguageDialogState.update {
                    if(it is CreateLanguageDialogState.Showed) {
                        it.copy(
                            languageName = event.value
                        )
                    } else it
                }
            }
        }
    }

    override val state: Flow<ScreenState> = combine(
        provideLanguageContract.languages,
        createLanguageDialogState,
        isRemovingInProcessState
    ) { languages,createLanguageDialogState,isRemovingInProcess ->
        ScreenState(
            languageList = languages,
            createLanguageDialogState = createLanguageDialogState,
            isRemovingInProcess = isRemovingInProcess
        ).also { cashedScreenState = it }
    }


    private var cashedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cashedScreenState

}