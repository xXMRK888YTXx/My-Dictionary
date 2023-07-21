package com.xxmrk888ytxx.createbackupscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
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
import javax.inject.Inject

class CreateBackupViewModel @Inject constructor(
    private val provideWordGroupsContract: ProvideWordGroupsContract
) : ViewModel(),UiModel<ScreenState> {

    private val selectedWordGroupsIdState:MutableStateFlow<ImmutableSet<Int>> = MutableStateFlow(
        persistentSetOf()
    )

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            is LocalUiEvent.ChangeWordGroupSelectStateEvent -> {
                selectedWordGroupsIdState.update {
                    it.insertOrRemove(event.wordGroupId)
                }
            }

            is LocalUiEvent.SelectBackupPlaceEvent -> {}
        }
    }

    override val state: Flow<ScreenState> = combine(
        provideWordGroupsContract.wordGroups,
        selectedWordGroupsIdState
    ) { availableWordGroups,selectedWordGroupsId ->
        ScreenState(
            availableWordGroups = availableWordGroups,
            selectedWordGroupId = selectedWordGroupsId
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