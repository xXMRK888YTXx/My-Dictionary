package com.xxmrk888ytxx.viewgroupwordsscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.androidcore.fastDebugLog
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.viewgroupwordsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.viewgroupwordsscreen.models.ScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViewGroupWordsViewModel @AssistedInject constructor(
    @Assisted private val wordGroupId:Int
) : ViewModel(),UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return


    }

    override val state: Flow<ScreenState> = flow { emit(ScreenState(persistentListOf())) }

    override val defValue: ScreenState
        get() = ScreenState(persistentListOf())


    @AssistedFactory
    interface Factory {
        fun create(wordGroupId:Int) : ViewGroupWordsViewModel
    }
}