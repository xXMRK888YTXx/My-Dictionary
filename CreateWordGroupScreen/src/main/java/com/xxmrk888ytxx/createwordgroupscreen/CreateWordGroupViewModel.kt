package com.xxmrk888ytxx.createwordgroupscreen

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.createwordgroupscreen.contract.CreateLanguageContract
import com.xxmrk888ytxx.createwordgroupscreen.contract.ProvideLanguagesContract
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
import java.lang.ClassCastException
import javax.inject.Inject

class CreateWordGroupViewModel @Inject constructor(
    private val createLanguageContract: CreateLanguageContract,
    private val provideLanguagesContract: ProvideLanguagesContract,
    private val logger: Logger
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
                if(event.imageUri == null) return

                try {
                    selectedImagePathFlow.update { event.imageUri.toString() }
                }catch (e:Exception) { logger.error(e, LOG_TAG) }
            }

            is LocalUiEvent.BackPageEvent -> {
                event.apply {
                    if(pagerState.currentPage == 0) {
                        navigator.backScreen()
                    } else {
                        uiScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                }
            }
        }
    }

    private val groupNameFlow: MutableStateFlow<String> = MutableStateFlow("")

    private val selectedImagePathFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    private val selectedPrimaryLanguage: MutableStateFlow<Language?> = MutableStateFlow(null)

    private val selectedSecondaryLanguage: MutableStateFlow<Language?> = MutableStateFlow(null)

    private val isAddWordGroupInProcess = MutableStateFlow(false)

    override val state: Flow<ScreenState> = combine(
        groupNameFlow,
        selectedImagePathFlow,
        provideLanguagesContract.languages,
        selectedPrimaryLanguage,
        selectedSecondaryLanguage,
        isAddWordGroupInProcess
    ) { flowArray ->
        try {
            ScreenState(
                newGroupName = flowArray[0] as String,
                imageGroupUrl = flowArray[1] as String?,
                languages = flowArray[2] as ImmutableList<Language>,
                selectedPrimaryLanguage = flowArray[3] as Language?,
                selectedSecondaryLanguage = flowArray[4] as Language?,
                isAddWordGroupInProcess = flowArray[5] as Boolean

            )
        }catch (e:ClassCastException) { logger.error(e, LOG_TAG);defValue }
    }

    override val defValue: ScreenState
        get() = ScreenState()

    companion object {
        private const val LOG_TAG = "CreateWordGroupViewModel"
    }
}