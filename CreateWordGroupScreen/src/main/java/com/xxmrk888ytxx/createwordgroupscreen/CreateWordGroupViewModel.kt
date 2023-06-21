package com.xxmrk888ytxx.createwordgroupscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.createwordgroupscreen.contract.CreateLanguageContract
import com.xxmrk888ytxx.createwordgroupscreen.contract.ProvideLanguagesContract
import com.xxmrk888ytxx.createwordgroupscreen.models.Language
import com.xxmrk888ytxx.createwordgroupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.createwordgroupscreen.models.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateWordGroupViewModel @Inject constructor(
    private val createLanguageContract: CreateLanguageContract,
    private val provideLanguagesContract: ProvideLanguagesContract,
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
        }
    }

    private val groupNameFlow: MutableStateFlow<String> = MutableStateFlow("")

    private val selectedImagePathFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    private val selectedPrimaryLanguage: MutableStateFlow<Language?> = MutableStateFlow(null)

    private val selectedSecondaryLanguage: MutableStateFlow<Language?> = MutableStateFlow(null)

    override val state: Flow<ScreenState> = combine(
        groupNameFlow,
        selectedImagePathFlow,
        provideLanguagesContract.languages,
        selectedPrimaryLanguage,
        selectedSecondaryLanguage
    ) { groupName, selectedImagePath, availableLanguages, selectedPrimaryLanguage, selectedSecondaryLanguage ->
        ScreenState(
            groupName, selectedImagePath, availableLanguages, selectedPrimaryLanguage,
            selectedSecondaryLanguage
        )
    }

    override val defValue: ScreenState
        get() = ScreenState()

}