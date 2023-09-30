package com.xxmrk888ytxx.translatorscreen

import android.content.ActivityNotFoundException
import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.coreandroid.cancelChildrenAndLaunch
import com.xxmrk888ytxx.coreandroid.getWithCast
import com.xxmrk888ytxx.translatorscreen.contract.ManagerCurrentLanguageForTranslate
import com.xxmrk888ytxx.translatorscreen.contract.ManagerCurrentOriginalWordLanguage
import com.xxmrk888ytxx.translatorscreen.contract.ProvideSupportedLanguages
import com.xxmrk888ytxx.translatorscreen.contract.ProvideTranslatorContract
import com.xxmrk888ytxx.translatorscreen.contract.TextToSpeechContract
import com.xxmrk888ytxx.translatorscreen.models.ChangeLanguageBottomSheetState
import com.xxmrk888ytxx.translatorscreen.models.LoadingModelsDialogState
import com.xxmrk888ytxx.translatorscreen.models.LocalUiEvent
import com.xxmrk888ytxx.translatorscreen.models.ScreenState
import com.xxmrk888ytxx.translatorscreen.models.TranslateState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class TranslatorViewModel @Inject constructor(
    private val textToSpeechContract: TextToSpeechContract,
    private val provideSupportedLanguages: ProvideSupportedLanguages,
    private val managerCurrentOriginalWordLanguage: ManagerCurrentOriginalWordLanguage,
    private val managerCurrentLanguageForTranslate: ManagerCurrentLanguageForTranslate,
    private val provideTranslatorContract: ProvideTranslatorContract,
) : ViewModel(), UiModel<ScreenState> {

    private val translateScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val loadingModelsScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @OptIn(ExperimentalMaterial3Api::class)
    override fun handleEvent(event: UiEvent) {
        if (event !is LocalUiEvent) return

        when (event) {

            is LocalUiEvent.TextForTranslateInput -> {
                updateTextForTranslateAndTranslate { event.text }
            }

            LocalUiEvent.ClearTextForTranslate -> {
                updateTextForTranslateAndTranslate { "" }
            }

            LocalUiEvent.AskTestEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    textToSpeechContract.textToSpeech(textForTranslate.value)
                }
            }

            is LocalUiEvent.RequestRecognizeSpeechForTextToTranslate -> {
                event.uiScope.launch {
                    try {
                        val screenState = state.first()

                        event.speechRecognizeContract.launch(screenState.currentOriginalLanguage.code)
                    } catch (e: ActivityNotFoundException) {
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

                    when (current) {

                        ChangeLanguageBottomSheetState.ChangingLanguageForTranslate -> {

                            if (managerCurrentOriginalWordLanguage.currentLanguage.first().code == event.language.code) return@launch

                            managerCurrentLanguageForTranslate.setupLanguage(event.language)
                        }

                        ChangeLanguageBottomSheetState.ChangingOriginalLanguage -> {
                            if (managerCurrentLanguageForTranslate.currentLanguage.first().code == event.language.code) return@launch

                            managerCurrentOriginalWordLanguage.setupLanguage(event.language)
                        }

                        ChangeLanguageBottomSheetState.Hidden -> {}
                    }
                }
            }

            LocalUiEvent.ExchangeLanguages -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val originalLanguage =
                        managerCurrentOriginalWordLanguage.currentLanguage.first()
                    val languageForTranslate =
                        managerCurrentLanguageForTranslate.currentLanguage.first()

                    managerCurrentLanguageForTranslate.setupLanguage(originalLanguage)
                    managerCurrentOriginalWordLanguage.setupLanguage(languageForTranslate)
                }
            }

            LocalUiEvent.DismissLoadingModelsDialogStateDialog -> {
                loadingModelsDialogState.update { LoadingModelsDialogState.Hidden }
            }

            is LocalUiEvent.RequestToDownloadModelsForTranslate -> {
                startLoadingTranslateModels(event.snackbarHostState,event.uiScope,event.context)
            }
        }
    }

    private fun startLoadingTranslateModels(
        snackbarHostState: SnackbarHostState,
        uiScope:CoroutineScope,
        context: Context
    ) {
        loadingModelsScope.cancelChildrenAndLaunch {

            val screenState = state.first()

            loadingModelsDialogState.update { LoadingModelsDialogState.Loading }

            val result = provideTranslatorContract.downloadModel(
                screenState.currentOriginalLanguage.code,
                screenState.currentLanguageForTranslate.code
            ).first()

            result
                .onSuccess {
                    loadingModelsDialogState.update { LoadingModelsDialogState.Hidden }

                    uiScope.launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.models_for_translating_was_download_successfully))
                    }
                }
                .onFailure { loadingModelsDialogState.update { LoadingModelsDialogState.Error } }
        }
    }

    private fun updateTextForTranslateAndTranslate(
        onUpdate: (String) -> String,
    ) {
        textForTranslate.update(onUpdate)

        sendTranslateRequest()
    }

    private fun sendTranslateRequest() {
        translateScope.cancelChildrenAndLaunch {

            delay(500)

            if(!isActive) return@cancelChildrenAndLaunch


            val screenState = state.first()

            translateStateFlow.update { TranslateState.Loading }


            val idModelDownloaded = provideTranslatorContract.isModelDownloaded(
                screenState.currentOriginalLanguage.code,
                screenState.currentLanguageForTranslate.code
            ).first()

            if (idModelDownloaded) {

                val translateResult = provideTranslatorContract.translate(
                    screenState.textForTranslate,
                    screenState.currentOriginalLanguage.code,
                    screenState.currentLanguageForTranslate.code
                ).first()

                translateResult
                    .onSuccess { text ->
                        translateStateFlow.update { TranslateState.Translated(text) }
                    }
                    .onFailure {
                        translateStateFlow.update { TranslateState.Error(R.string.maybe_models_for_translating_didn_t_install_retry_one_more_time) }
                    }

            } else {
                loadingModelsDialogState.update { LoadingModelsDialogState.OfferToDownload }
                translateStateFlow.update { TranslateState.None }
            }
        }
    }

    private val textForTranslate = MutableStateFlow("")


    private val translateStateFlow = MutableStateFlow<TranslateState>(TranslateState.None)

    private val loadingModelsDialogState = MutableStateFlow<LoadingModelsDialogState>(LoadingModelsDialogState.Hidden)

    private val changeLanguageBottomSheetStateFlow =
        MutableStateFlow<ChangeLanguageBottomSheetState>(ChangeLanguageBottomSheetState.Hidden)

    override val state: Flow<ScreenState> = combine(
        textForTranslate,
        flowOf(provideSupportedLanguages.supportedLanguages),
        managerCurrentOriginalWordLanguage.currentLanguage,
        managerCurrentLanguageForTranslate.currentLanguage,
        changeLanguageBottomSheetStateFlow,
        translateStateFlow,
        loadingModelsDialogState
    ) { flowArray: Array<Any> ->
        ScreenState(
            textForTranslate = flowArray.getWithCast(0),
            supportedLanguageList = flowArray.getWithCast(1),
            currentOriginalLanguage = flowArray.getWithCast(2),
            currentLanguageForTranslate = flowArray.getWithCast(3),
            changeLanguageBottomSheetState = flowArray.getWithCast(4),
            translateState = flowArray.getWithCast(5),
            loadingModelsDialogState = flowArray.getWithCast(6)
        ).also {
            cashedState = it
        }
    }

    private var cashedState = ScreenState()

    override val defValue: ScreenState
        get() = cashedState


    override fun onCleared() {
        translateScope.cancel()
        super.onCleared()
    }
}