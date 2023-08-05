package com.xxmrk888ytxx.createbackupscreen.models

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import com.xxmrk888ytxx.coreandroid.ActivityContracts.RequestExternalFileContract.RequestExternalFileContractParams
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

sealed class LocalUiEvent : UiEvent {

    class SelectBackupPlaceEvent(
        val pickSaveLocationContract: ManagedActivityResultLauncher<RequestExternalFileContractParams, Uri?>,
    ) : LocalUiEvent()

    data class ChangeWordGroupSelectStateEvent(val wordGroupId: Int) : LocalUiEvent()

    class LocationSelectedEvent(
        val fileUri: Uri?,
        val navigator: Navigator,
    ) : LocalUiEvent()
}
