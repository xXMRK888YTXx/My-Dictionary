package com.xxmrk888ytxx.wordgroupscreen.models

import AdController
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

sealed class LocalUiEvent : UiEvent {

    class FloatButtonClickEvent(val navigator: Navigator,val adController: AdController) : LocalUiEvent()

    class AddFirstWordGroupButtonClickEvent(val navigator: Navigator,val adController: AdController) : LocalUiEvent()

    class OpenWordGroupEvent(
        val navigator: Navigator,
        val wordGroup: WordGroup,
        val adController: AdController
    ) : LocalUiEvent()

    object HideWordGroupDialogOption : LocalUiEvent()

    data class ShowWordGroupDialogOptionState(val wordGroupId: Int, val isHaveImage: Boolean) :
        LocalUiEvent()

    data class RemoveWordGroupEvent(val wordGroupId: Int) : LocalUiEvent()

    class RemoveImageEvent(val wordGroupId: Int) : LocalUiEvent()

    class OnImagePickedEvent(val uri: Uri?) : LocalUiEvent()

    class AttachImageRequest(
        val selectImageContract: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    ) : LocalUiEvent()

}
