package com.xxmrk888ytxx.restorebackupscreen.models

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.material3.SnackbarHostState
import com.xxmrk888ytxx.coreandroid.ActivityContracts.OpenExternalFileContract.OpenExternalFileContractParams
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import kotlinx.coroutines.CoroutineScope

sealed class LocalUiEvent : UiEvent {

    class FileSelectedEvent(
        val uri: Uri?,
        val snackbarHostState: SnackbarHostState,
        val uiScope: CoroutineScope,
        val navigator: Navigator
    ) : LocalUiEvent()

    class SelectFileRequestEvent(
        val selectFileContract: ManagedActivityResultLauncher<OpenExternalFileContractParams, Uri?>,
    ) : LocalUiEvent()


}
