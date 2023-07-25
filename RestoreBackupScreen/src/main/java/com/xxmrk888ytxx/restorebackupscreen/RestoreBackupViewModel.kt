package com.xxmrk888ytxx.restorebackupscreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ActivityContracts.OpenExternalFileContract.OpenExternalFileContractParams
import com.xxmrk888ytxx.coreandroid.Const
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.ToastManager
import com.xxmrk888ytxx.restorebackupscreen.contract.RestoreBackupContract
import com.xxmrk888ytxx.restorebackupscreen.exception.BadBackupFileException
import com.xxmrk888ytxx.restorebackupscreen.exception.FileNotBackupFileException
import com.xxmrk888ytxx.restorebackupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.restorebackupscreen.models.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class RestoreBackupViewModel @Inject constructor(
    private val restoreBackupContract: RestoreBackupContract,
    private val toastManager: ToastManager,
    private val context: Context
) : ViewModel(), UiModel<ScreenState> {

    private val isRestoreInProcessState = MutableStateFlow(false)

    @SuppressLint("ResourceType")
    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            is LocalUiEvent.FileSelectedEvent -> {
                if(event.uri == null) {
                    return
                }

                isRestoreInProcessState.update { true }

                viewModelScope.launch(Dispatchers.IO) {
                    restoreBackupContract.restore(event.uri)
                        .onFailure {
                            event.uiScope.launch {
                                val exceptionText = when(it) {
                                    is BadBackupFileException -> context.getString(R.string.backup_file_is_damaged)

                                    is FileNotBackupFileException -> context.getString(R.string.selected_file_is_not_backup)

                                    else -> context.getString(R.string.unknown_error) + " (errorName:${it::class.simpleName}; errorMessage:${it.message})"
                                }

                                event.snackbarHostState.showSnackbar(exceptionText)
                            }

                            isRestoreInProcessState.update { false }
                        }
                        .onSuccess {
                            event.navigator.backScreen()
                            toastManager.showToast(R.string.backup_is_restored)
                        }
                }
            }

            is LocalUiEvent.SelectFileRequestEvent -> {
                event.selectFileContract.launch(OpenExternalFileContractParams(
                    "*/*"
                ))


            }
        }
    }

    override val state: Flow<ScreenState> = isRestoreInProcessState.map { isRestoreInProcess ->
        ScreenState(isRestoreInProcess).also { cashedScreenState = it }
    }

    private var cashedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cashedScreenState
}