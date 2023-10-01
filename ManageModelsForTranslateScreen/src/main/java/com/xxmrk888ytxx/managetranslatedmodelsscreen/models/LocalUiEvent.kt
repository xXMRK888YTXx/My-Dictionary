package com.xxmrk888ytxx.managetranslatedmodelsscreen.models

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import kotlinx.coroutines.CoroutineScope

sealed interface LocalUiEvent : UiEvent {

    class BackScreenEvent(val navigator: Navigator) : LocalUiEvent

    class RemoveTranslateModel(
        val code: String,
        val snackbarHostState: SnackbarHostState,
        val uiScope: CoroutineScope,
        val context: Context
    ) : LocalUiEvent
}