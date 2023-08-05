package com.xxmrk888ytxx.managelanguagescreen.models

import androidx.compose.material3.SnackbarHostState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import kotlinx.coroutines.CoroutineScope

sealed class LocalUiEvent : UiEvent {
    
    object HideCreateNewLanguageDialog : LocalUiEvent()

    object ShowCreateNewLanguageDialog : LocalUiEvent()

    class CreateNewLanguage(
        val snackbarHostState: SnackbarHostState,
        val scope: CoroutineScope
    ) : LocalUiEvent()

    class RemoveLanguageEvent(
        val id: Int,
        val snackbarHostState: SnackbarHostState,
        val uiScope: CoroutineScope
    ) : LocalUiEvent()

    class BackScreenEvent(
        val navigator: Navigator
    ) : LocalUiEvent()

    class NewLanguageNameEnteredEvent(val value: String) : LocalUiEvent()


}
