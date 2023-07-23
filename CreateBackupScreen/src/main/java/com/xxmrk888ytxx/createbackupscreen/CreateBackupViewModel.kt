package com.xxmrk888ytxx.createbackupscreen

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ActivityContracts.RequestExternalFileContract.RequestExternalFileContract
import com.xxmrk888ytxx.coreandroid.ActivityContracts.RequestExternalFileContract.RequestExternalFileContractParams
import com.xxmrk888ytxx.coreandroid.Const
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.ToastManager
import com.xxmrk888ytxx.createbackupscreen.contract.CreateBackupContract
import com.xxmrk888ytxx.createbackupscreen.contract.ProvideWordGroupsContract
import com.xxmrk888ytxx.createbackupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.createbackupscreen.models.ScreenState
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CreateBackupViewModel @Inject constructor(
    private val provideWordGroupsContract: ProvideWordGroupsContract,
    private val createBackupContract: CreateBackupContract,
    private val toastManager: ToastManager
) : ViewModel(),UiModel<ScreenState> {

    private val selectedWordGroupsIdState:MutableStateFlow<ImmutableSet<Int>> = MutableStateFlow(
        persistentSetOf()
    )

    private val isBackupInProcessState = MutableStateFlow(false)

    @SuppressLint("ResourceType")
    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            is LocalUiEvent.ChangeWordGroupSelectStateEvent -> {
                selectedWordGroupsIdState.update {
                    it.insertOrRemove(event.wordGroupId)
                }
            }

            is LocalUiEvent.SelectBackupPlaceEvent -> {
                val params = RequestExternalFileContractParams(
                    fileType = "application/${Const.BACKUP_FILE_TYPE}",
                    startFileName = "backup.${Const.BACKUP_FILE_TYPE}"
                )

                event.pickSaveLocationContract.launch(params)
            }

            is LocalUiEvent.LocationSelectedEvent -> {
                if(event.fileUri != null) {
                    isBackupInProcessState.update { true }

                    viewModelScope.launch(Dispatchers.IO) {
                        createBackupContract.createBackup(
                            selectedWordGroupsIdState.value,
                            event.fileUri
                        ).onFailure { 
                            isBackupInProcessState.update { false }

                            toastManager.showToast(R.string.in_backup_process_was_been_error)
                        }.onSuccess {
                            withContext(Dispatchers.Main) {
                                event.navigator.backScreen()
                            }
                            
                            toastManager.showToast(R.string.backup_created)
                        }
                    }

                } else {
                    toastManager.showToast(R.string.location_selection_has_been_canceled)
                }
            }
        }
    }

    override val state: Flow<ScreenState> = combine(
        provideWordGroupsContract.wordGroups,
        selectedWordGroupsIdState,
        isBackupInProcessState
    ) { availableWordGroups,selectedWordGroupsId,isBackupInProcess ->
        ScreenState(
            availableWordGroups = availableWordGroups,
            selectedWordGroupId = selectedWordGroupsId,
            isBackupInProcess = isBackupInProcess
        ).also { cashedScreenState = it }
    }

    private var cashedScreenState = ScreenState()


    override val defValue: ScreenState
        get() = ScreenState()

    private fun Set<Int>.insertOrRemove(id: Int): ImmutableSet<Int> {
        val isHaveIdInSet = contains(id)
        val newSet = mutableSetOf<Int>()

        forEach {
            newSet.add(it)
        }


        if (isHaveIdInSet) {
            newSet.remove(id)
        } else {
            newSet.add(id)
        }

        return newSet.toImmutableSet()
    }

    init {
        //Mark all groups of words to use in training
        val alreadyMarkedId = hashSetOf<Int>()

        viewModelScope.launch(Dispatchers.Default) {
            provideWordGroupsContract.wordGroups.collect() {
                it.forEach { wordGroup ->
                    if(!alreadyMarkedId.contains(wordGroup.id)) {
                        alreadyMarkedId.add(wordGroup.id)

                        handleEvent(LocalUiEvent.ChangeWordGroupSelectStateEvent(wordGroup.id))
                    }
                }
            }
        }
    }

}