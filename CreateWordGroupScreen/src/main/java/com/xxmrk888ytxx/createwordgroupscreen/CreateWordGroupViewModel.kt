package com.xxmrk888ytxx.createwordgroupscreen

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.createwordgroupscreen.contract.CreateLanguageContract
import com.xxmrk888ytxx.createwordgroupscreen.contract.CreateWorkGroupContract
import com.xxmrk888ytxx.createwordgroupscreen.contract.ProvideLanguagesContract
import com.xxmrk888ytxx.createwordgroupscreen.models.CreateNewLanguageDialogState
import com.xxmrk888ytxx.createwordgroupscreen.models.Language
import com.xxmrk888ytxx.createwordgroupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.createwordgroupscreen.models.ScreenState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException
import javax.inject.Inject

class CreateWordGroupViewModel @Inject constructor(
    private val createLanguageContract: CreateLanguageContract,
    private val provideLanguagesContract: ProvideLanguagesContract,
    private val logger: Logger,
    private val createWorkGroupContract: CreateWorkGroupContract
) : ViewModel(), UiModel<ScreenState> {


    @OptIn(ExperimentalFoundationApi::class)
    override fun handleEvent(event: UiEvent) {
        if (event !is LocalUiEvent) return

        when (event) {
            is LocalUiEvent.TitleTextChangedEvent -> {
                groupNameFlow.update { event.text }
            }

            is LocalUiEvent.WordGroupNameInputCompletedEvent -> {
                event.uiScope.launch(Dispatchers.Default) {

                    event.pagerState.animateScrollToPage(event.pagerState.currentPage + 1)
                }
            }

            is LocalUiEvent.SelectNewPrimaryLanguageEvent -> {
                selectedPrimaryLanguage.update { event.language }
            }

            is LocalUiEvent.SelectNewSecondaryLanguageEvent -> {
                selectedSecondaryLanguage.update { event.language }
            }

            is LocalUiEvent.LanguageSelectCompletedEvent -> {
                event.uiScope.launch {
                    event.pagerState.animateScrollToPage(event.pagerState.currentPage + 1)
                }
            }

            is LocalUiEvent.PickImageRequestEvent -> {
                event.contract.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            is LocalUiEvent.ImagePickedEvent -> {
                if (event.imageUri == null) return

                try {
                    selectedImagePathFlow.update { event.imageUri.toString() }
                } catch (e: Exception) {
                    logger.error(e, LOG_TAG)
                }
            }

            is LocalUiEvent.BackPageEvent -> {
                event.apply {
                    if (pagerState.currentPage == 0) {
                        navigator.backScreen()
                    } else {
                        uiScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                }
            }

            LocalUiEvent.ConfigurationNewLanguageCompletedEvent -> {
                val dialogState = createNewLanguageDialogState.value

                if (dialogState is CreateNewLanguageDialogState.Showed) {
                    createNewLanguageDialogState.update {
                        if (it is CreateNewLanguageDialogState.Showed) it.copy(
                            isAddingInProcess = true
                        ) else it
                    }

                    viewModelScope.launch(Dispatchers.IO) {
                        createLanguageContract.newLanguage(dialogState.newLanguageText)

                        createNewLanguageDialogState.update { CreateNewLanguageDialogState.Hidden }
                    }
                }
            }

            LocalUiEvent.HideCreateNewLanguageDialogEvent -> {

                if (
                    (createNewLanguageDialogState.value as? CreateNewLanguageDialogState.Showed)
                        ?.isAddingInProcess == true
                ) return

                createNewLanguageDialogState.update { CreateNewLanguageDialogState.Hidden }
            }

            is LocalUiEvent.InputTextForLanguageNameEvent -> {
                createNewLanguageDialogState.update {
                    if (it is CreateNewLanguageDialogState.Showed) it.copy(newLanguageText = event.text)
                    else it
                }
            }

            LocalUiEvent.ShowCreateNewLanguageDialogEvent -> {
                createNewLanguageDialogState.update { CreateNewLanguageDialogState.Showed() }
            }

            is LocalUiEvent.WordGroupCreateCompleted -> {
                if(isCreateWordGroupInProcess.value) return

                isCreateWordGroupInProcess.update { true }

                viewModelScope.launch(Dispatchers.IO) {
                    createWorkGroupContract.createWordGroup(
                        groupName = groupNameFlow.value,
                        primaryLanguage = selectedPrimaryLanguage.value ?: return@launch,
                        secondLanguage = selectedSecondaryLanguage.value ?: return@launch,
                        imageUrl = selectedImagePathFlow.value
                    )

                    event.navigator.backScreen()
                }
            }
        }
    }

    private val groupNameFlow: MutableStateFlow<String> = MutableStateFlow("")

    private val selectedImagePathFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    private val selectedPrimaryLanguage: MutableStateFlow<Language?> = MutableStateFlow(null)

    private val selectedSecondaryLanguage: MutableStateFlow<Language?> = MutableStateFlow(null)

    private val isAddWordGroupInProcess = MutableStateFlow(false)

    private val createNewLanguageDialogState: MutableStateFlow<CreateNewLanguageDialogState> =
        MutableStateFlow(CreateNewLanguageDialogState.Hidden)

    private val isCreateWordGroupInProcess:MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val state: Flow<ScreenState> = combine(
        groupNameFlow,
        selectedImagePathFlow,
        provideLanguagesContract.languages,
        selectedPrimaryLanguage,
        selectedSecondaryLanguage,
        isAddWordGroupInProcess,
        createNewLanguageDialogState,
        isCreateWordGroupInProcess
    ) { flowArray ->
        try {
            ScreenState(
                newGroupName = flowArray[0] as String,
                imageGroupUrl = flowArray[1] as String?,
                languages = flowArray[2] as ImmutableList<Language>,
                selectedPrimaryLanguage = flowArray[3] as Language?,
                selectedSecondaryLanguage = flowArray[4] as Language?,
                isAddWordGroupInProcess = flowArray[5] as Boolean,
                createNewLanguageDialogState = flowArray[6] as CreateNewLanguageDialogState,
                isCreateWordGroupInProcess = flowArray[7] as Boolean
            )
        } catch (e: ClassCastException) {
            logger.error(e, LOG_TAG)

            defValue
        }
    }

    override val defValue: ScreenState
        get() = ScreenState()

    companion object {
        private const val LOG_TAG = "CreateWordGroupViewModel"
    }
}