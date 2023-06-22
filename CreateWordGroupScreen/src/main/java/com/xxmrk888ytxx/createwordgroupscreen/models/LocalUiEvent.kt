@file:OptIn(ExperimentalFoundationApi::class)

package com.xxmrk888ytxx.createwordgroupscreen.models

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import kotlinx.coroutines.CoroutineScope

internal sealed class LocalUiEvent : UiEvent {
    object HideCreateNewLanguageDialogEvent : LocalUiEvent()
    object ConfigurationNewLanguageCompletedEvent : LocalUiEvent()
    object ShowCreateNewLanguageDialogEvent : LocalUiEvent()

    class TitleTextChangedEvent(val text: String) : LocalUiEvent()

    class WordGroupNameInputCompletedEvent(
        val pagerState: PagerState,
        val uiScope: CoroutineScope,
    ) : LocalUiEvent()

    data class SelectNewPrimaryLanguageEvent(val language: Language) : LocalUiEvent()

    data class SelectNewSecondaryLanguageEvent(val language: Language) : LocalUiEvent()

    class LanguageSelectCompletedEvent(val pagerState: PagerState, val uiScope: CoroutineScope) :
        LocalUiEvent()

    class PickImageRequestEvent(val contract: ActivityResultLauncher<PickVisualMediaRequest>) :
        LocalUiEvent()

    class ImagePickedEvent(val imageUri: Uri?) : LocalUiEvent()

    class BackPageEvent(
        val pagerState: PagerState,
        val uiScope: CoroutineScope,
        val navigator: Navigator,
    ) : LocalUiEvent()

    data class InputTextForLanguageNameEvent(val text: String) : LocalUiEvent()

}