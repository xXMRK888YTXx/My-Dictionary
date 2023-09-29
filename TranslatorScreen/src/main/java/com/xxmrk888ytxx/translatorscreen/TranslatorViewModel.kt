package com.xxmrk888ytxx.translatorscreen

import android.content.ActivityNotFoundException
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.coreandroid.getWithCast
import com.xxmrk888ytxx.translatorscreen.contract.ManagerCurrentLanguageForTranslate
import com.xxmrk888ytxx.translatorscreen.contract.ManagerCurrentOriginalWordLanguage
import com.xxmrk888ytxx.translatorscreen.contract.ProvideSupportedLanguages
import com.xxmrk888ytxx.translatorscreen.contract.TextToSpeechContract
import com.xxmrk888ytxx.translatorscreen.models.ChangeLanguageBottomSheetState
import com.xxmrk888ytxx.translatorscreen.models.LocalUiEvent
import com.xxmrk888ytxx.translatorscreen.models.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class TranslatorViewModel @Inject constructor(
    private val textToSpeechContract: TextToSpeechContract,
    private val provideSupportedLanguages: ProvideSupportedLanguages,
    private val managerCurrentOriginalWordLanguage: ManagerCurrentOriginalWordLanguage,
    private val managerCurrentLanguageForTranslate: ManagerCurrentLanguageForTranslate
) : ViewModel(), UiModel<ScreenState> {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun handleEvent(event: UiEvent) {
        if (event !is LocalUiEvent) return

        when (event) {
            is LocalUiEvent.TextForTranslateInput -> {
                textForTranslate.update { event.text }
            }

            LocalUiEvent.ClearTextForTranslate -> {
                updateTextForTranslateAndTranslate { "" }
            }

            LocalUiEvent.AskTestEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    textToSpeechContract.textToSpeech(textForTranslate.value)
                }
            }

            is LocalUiEvent.RequestRecognizeSpeech -> {
                try {
                    event.speechRecognizeContract.launch("en")
                } catch (e: ActivityNotFoundException) {
                    event.uiScope.launch {
                        event.snackbarHostState.showSnackbar(
                            event.context.getString(R.string.could_not_found_app_for_recognize_speech)
                        )
                    }
                }
            }

            is LocalUiEvent.SpeechRecognizedEvent -> {
                updateTextForTranslateAndTranslate { it + event.text }
            }

            is LocalUiEvent.PastTextFromClipboard -> {
                val text = event.text ?: return

                updateTextForTranslateAndTranslate { it + text }
            }

            is LocalUiEvent.BottomSheetDismissRequest -> {
                changeLanguageBottomSheetStateFlow.update { ChangeLanguageBottomSheetState.Hidden }
            }

            is LocalUiEvent.ShowListForChangeLanguageForTranslate -> {
                changeLanguageBottomSheetStateFlow.update { ChangeLanguageBottomSheetState.ChangingLanguageForTranslate }
            }

            is LocalUiEvent.ShowListForChangeOriginalLanguage -> {
                changeLanguageBottomSheetStateFlow.update { ChangeLanguageBottomSheetState.ChangingOriginalLanguage }
            }

            is LocalUiEvent.ChangeSelectedLanguage -> {

                viewModelScope.launch(Dispatchers.IO) {

                    val current = changeLanguageBottomSheetStateFlow.value

                    changeLanguageBottomSheetStateFlow.update { ChangeLanguageBottomSheetState.Hidden }

                    when(current) {

                        ChangeLanguageBottomSheetState.ChangingLanguageForTranslate -> {

                            if(managerCurrentOriginalWordLanguage.currentLanguage.first().code == event.language.code) return@launch

                            managerCurrentLanguageForTranslate.setupLanguage(event.language)
                        }

                        ChangeLanguageBottomSheetState.ChangingOriginalLanguage -> {
                            if(managerCurrentLanguageForTranslate.currentLanguage.first().code == event.language.code) return@launch

                            managerCurrentOriginalWordLanguage.setupLanguage(event.language)
                        }

                        ChangeLanguageBottomSheetState.Hidden -> {}
                    }
                }
            }

            LocalUiEvent.ExchangeLanguages -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val originalLanguage = managerCurrentOriginalWordLanguage.currentLanguage.first()
                    val languageForTranslate = managerCurrentLanguageForTranslate.currentLanguage.first()

                    managerCurrentLanguageForTranslate.setupLanguage(originalLanguage)
                    managerCurrentOriginalWordLanguage.setupLanguage(languageForTranslate)
                }
            }
        }
    }

    private fun updateTextForTranslateAndTranslate(
        onUpdate: (String) -> String,
    ) {
        textForTranslate.update(onUpdate)
    }

    private val textForTranslate = MutableStateFlow("")

    private val changeLanguageBottomSheetStateFlow = MutableStateFlow<ChangeLanguageBottomSheetState>(ChangeLanguageBottomSheetState.Hidden)

    override val state: Flow<ScreenState> = combine(
        textForTranslate,
        flowOf(provideSupportedLanguages.supportedLanguages),
        managerCurrentOriginalWordLanguage.currentLanguage,
        managerCurrentLanguageForTranslate.currentLanguage,
        changeLanguageBottomSheetStateFlow
    ) { flowArray: Array<Any> ->
        ScreenState(
            textForState = flowArray.getWithCast(0),
            supportedLanguageList = flowArray.getWithCast(1),
            currentOriginalLanguage = flowArray.getWithCast(2),
            currentLanguageForTranslate = flowArray.getWithCast(3),
            changeLanguageBottomSheetState = flowArray.getWithCast(4)
        ).also {
            cashedState = it
        }
    }

    private var cashedState = ScreenState()

    override val defValue: ScreenState
        get() = cashedState
}