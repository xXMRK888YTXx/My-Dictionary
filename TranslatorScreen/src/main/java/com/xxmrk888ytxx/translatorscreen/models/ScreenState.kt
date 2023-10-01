package com.xxmrk888ytxx.translatorscreen.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ScreenState(
    val textForTranslate:String = "",
    val supportedLanguageList:List<SupportedLanguage> = emptyList(),
    val currentOriginalLanguage:SupportedLanguage = SupportedLanguage("en",0),
    val currentLanguageForTranslate:SupportedLanguage = SupportedLanguage("ru",0),
    val changeLanguageBottomSheetState:ChangeLanguageBottomSheetState = ChangeLanguageBottomSheetState.Hidden,
    val translateState: TranslateState = TranslateState.None,
    val loadingModelsDialogState:LoadingModelsDialogState = LoadingModelsDialogState.Hidden,
    val fastAddWordInDictionaryBottomSheetState: FastAddWordInDictionaryBottomSheetState = FastAddWordInDictionaryBottomSheetState.Hidden,
    val availableWordGroups:ImmutableList<WordGroup> = persistentListOf()
)
