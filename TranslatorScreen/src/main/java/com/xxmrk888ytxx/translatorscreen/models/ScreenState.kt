package com.xxmrk888ytxx.translatorscreen.models

data class ScreenState(
    val textForTranslate:String = "",
    val supportedLanguageList:List<SupportedLanguage> = emptyList(),
    val currentOriginalLanguage:SupportedLanguage = SupportedLanguage("en",0),
    val currentLanguageForTranslate:SupportedLanguage = SupportedLanguage("ru",0),
    val changeLanguageBottomSheetState:ChangeLanguageBottomSheetState = ChangeLanguageBottomSheetState.Hidden,
    val translateState: TranslateState = TranslateState.None,
    val loadingModelsDialogState:LoadingModelsDialogState = LoadingModelsDialogState.Hidden
)
