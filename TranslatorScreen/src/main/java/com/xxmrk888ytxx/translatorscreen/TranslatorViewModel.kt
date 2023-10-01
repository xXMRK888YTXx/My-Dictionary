package com.xxmrk888ytxx.translatorscreen

import android.content.ActivityNotFoundException
import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.coreandroid.cancelChildrenAndLaunch
import com.xxmrk888ytxx.coreandroid.getWithCast
import com.xxmrk888ytxx.translatorscreen.contract.FastSaveWordInWordGroupContract
import com.xxmrk888ytxx.translatorscreen.contract.ManagerCurrentLanguageForTranslate
import com.xxmrk888ytxx.translatorscreen.contract.ManagerCurrentOriginalWordLanguage
import com.xxmrk888ytxx.translatorscreen.contract.ProvideSupportedLanguages
import com.xxmrk888ytxx.translatorscreen.contract.ProvideTranslatorContract
import com.xxmrk888ytxx.translatorscreen.contract.ProvideWordGroupInfo
import com.xxmrk888ytxx.translatorscreen.contract.TextToSpeechContract
import com.xxmrk888ytxx.translatorscreen.models.ChangeLanguageBottomSheetState
import com.xxmrk888ytxx.translatorscreen.models.FastAddWordInDictionaryBottomSheetState
import com.xxmrk888ytxx.translatorscreen.models.LoadingModelsDialogState
import com.xxmrk888ytxx.translatorscreen.models.LocalUiEvent
import com.xxmrk888ytxx.translatorscreen.models.ScreenState
import com.xxmrk888ytxx.translatorscreen.models.TranslateState
import com.xxmrk888ytxx.translatorscreen.models.WordGroup
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TranslatorViewModel @Inject constructor(
    private val textToSpeechContract: TextToSpeechContract,
    private val provideSupportedLanguages: ProvideSupportedLanguages,
    private val managerCurrentOriginalWordLanguage: ManagerCurrentOriginalWordLanguage,
    private val managerCurrentLanguageForTranslate: ManagerCurrentLanguageForTranslate,
    private val provideTranslatorContract: ProvideTranslatorContract,
    private val provideWordGroupInfo: ProvideWordGroupInfo,
    private val fastSaveWordInWordGroupContract: FastSaveWordInWordGroupContract
) : ViewModel(), UiModel<ScreenState> {

    private val translateScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val loadingModelsScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private var lastSelectedForFastAddingWordGroup:WordGroup? = null

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

            LocalUiEvent.AskTextForTranslateEvent -> {
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

                    updateTextForTranslateAndTranslate(false) { it }
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

                    val screenState = state.first()

                    if(screenState.translateState is TranslateState.Translated) {
                        val translatedText = (screenState.translateState as? TranslateState.Translated)?.translatedText ?: return@launch

                        updateTextForTranslateAndTranslate(false) { translatedText }
                    }
                }
            }

            LocalUiEvent.DismissLoadingModelsDialogStateDialog -> {
                loadingModelsDialogState.update { LoadingModelsDialogState.Hidden }
            }

            is LocalUiEvent.RequestToDownloadModelsForTranslate -> {
                startLoadingTranslateModels(event.snackbarHostState,event.uiScope,event.context)
            }

            LocalUiEvent.AskTranslatedTextEvent -> {
                viewModelScope.launch(Dispatchers.Default) {
                    val screenState = state.first()

                    val text = (screenState.translateState as? TranslateState.Translated)?.translatedText ?: return@launch

                    textToSpeechContract.textToSpeech(text)
                }
            }

            is LocalUiEvent.CopyTranslatedTextingBuffer -> {
                viewModelScope.launch {
                    val screenState = state.first()

                    val text = (screenState.translateState as? TranslateState.Translated)?.translatedText ?: return@launch

                    withContext(Dispatchers.Main) {
                        event.clipboardManager.setText(AnnotatedString(text))
                    }
                }
            }

            LocalUiEvent.ShowFastAddWordInDictionaryBottomSheet -> {
                viewModelScope.launch {
                    val screenState = state.first()

                    fastAddWordInDictionaryBottomSheetState.update {
                        FastAddWordInDictionaryBottomSheetState.Showed(
                            originalWord = screenState.textForTranslate,
                            translation = (screenState.translateState as? TranslateState.Translated)?.translatedText ?: "",
                            selectedWordGroup = if(lastSelectedForFastAddingWordGroup != null)
                                screenState.availableWordGroups.firstOrNull {
                                    it.id == (lastSelectedForFastAddingWordGroup?.id ?: Int.MIN_VALUE)
                                }
                                else null
                        )
                    }
                }
            }

            LocalUiEvent.DismissFastAddWordInDictionaryBottomSheet -> {
                fastAddWordInDictionaryBottomSheetState.update { FastAddWordInDictionaryBottomSheetState.Hidden }
            }

            is LocalUiEvent.UpdateStateForFastAddWordInDictionaryBottomSheet -> {
                if(fastAddWordInDictionaryBottomSheetState.value !is FastAddWordInDictionaryBottomSheetState.Showed) return

                fastAddWordInDictionaryBottomSheetState.update { event.state }
            }

            is LocalUiEvent.FastSaveWordEvent -> {
                val dialogState = (fastAddWordInDictionaryBottomSheetState.value as? FastAddWordInDictionaryBottomSheetState.Showed) ?: return

                if(dialogState.selectedWordGroup == null) return

                fastAddWordInDictionaryBottomSheetState.update { FastAddWordInDictionaryBottomSheetState.Hidden }

                lastSelectedForFastAddingWordGroup = dialogState.selectedWordGroup

                viewModelScope.launch(Dispatchers.IO) {
                    fastSaveWordInWordGroupContract.saveWord(
                        wordGroupId = dialogState.selectedWordGroup.id,
                        word = dialogState.originalWord,
                        translation = dialogState.translation,
                        transcription = dialogState.transcription
                    )
                        .onSuccess {
                            event.uiScope.launch {
                                event.snackbarHostState.showSnackbar( event.context.getString(R.string.the_word_was_successfully_added))
                            }
                        }
                        .onFailure {
                            event.uiScope.launch {
                                event.snackbarHostState.showSnackbar(event.context.getString(R.string.an_error_occurred_while_word_adding))
                            }
                        }
                }
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

                    updateTextForTranslateAndTranslate(false) { it }

                    uiScope.launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.models_for_translating_was_download_successfully))
                    }
                }
                .onFailure { loadingModelsDialogState.update { LoadingModelsDialogState.Error } }
        }
    }

    private fun updateTextForTranslateAndTranslate(
        isNeedDelayBeforeTranslating:Boolean = true,
        onUpdate: (String) -> String,
    ) {
        textForTranslate.update(onUpdate)

        translateScope.cancelChildrenAndLaunch {
            if(isNeedDelayBeforeTranslating) {
                delay(250)

                if(!isActive) return@cancelChildrenAndLaunch
            }

            sendTranslateRequest()
        }
    }

    private suspend fun sendTranslateRequest() {
        val screenState = state.first()

        if(screenState.textForTranslate.isNotEmpty())
            translateStateFlow.update { TranslateState.Loading }
        else {
            translateStateFlow.update { TranslateState.None }
            return
        }


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

    private val textForTranslate = MutableStateFlow("")


    private val translateStateFlow = MutableStateFlow<TranslateState>(TranslateState.None)

    private val loadingModelsDialogState = MutableStateFlow<LoadingModelsDialogState>(LoadingModelsDialogState.Hidden)

    private val changeLanguageBottomSheetStateFlow =
        MutableStateFlow<ChangeLanguageBottomSheetState>(ChangeLanguageBottomSheetState.Hidden)

    private val fastAddWordInDictionaryBottomSheetState = MutableStateFlow<FastAddWordInDictionaryBottomSheetState>(FastAddWordInDictionaryBottomSheetState.Hidden)


    override val state: Flow<ScreenState> = combine(
        textForTranslate,
        flowOf(provideSupportedLanguages.supportedLanguages),
        managerCurrentOriginalWordLanguage.currentLanguage,
        managerCurrentLanguageForTranslate.currentLanguage,
        changeLanguageBottomSheetStateFlow,
        translateStateFlow,
        loadingModelsDialogState,
        fastAddWordInDictionaryBottomSheetState,
        provideWordGroupInfo.wordGroups
    ) { flowArray: Array<Any> ->
        ScreenState(
            textForTranslate = flowArray.getWithCast(0),
            supportedLanguageList = flowArray.getWithCast(1),
            currentOriginalLanguage = flowArray.getWithCast(2),
            currentLanguageForTranslate = flowArray.getWithCast(3),
            changeLanguageBottomSheetState = flowArray.getWithCast(4),
            translateState = flowArray.getWithCast(5),
            loadingModelsDialogState = flowArray.getWithCast(6),
            fastAddWordInDictionaryBottomSheetState = flowArray.getWithCast(7),
            availableWordGroups = flowArray.getWithCast(8)
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