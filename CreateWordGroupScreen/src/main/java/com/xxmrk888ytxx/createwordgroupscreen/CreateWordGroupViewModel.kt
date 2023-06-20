package com.xxmrk888ytxx.createwordgroupscreen

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.createwordgroupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.createwordgroupscreen.models.Pages
import com.xxmrk888ytxx.createwordgroupscreen.models.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateWordGroupViewModel @Inject constructor(

) : ViewModel(),UiModel<ScreenState> {


    @OptIn(ExperimentalFoundationApi::class)
    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            is LocalUiEvent.TitleTextChangedEvent -> {
                groupNameFlow.update { event.text }
            }

            is LocalUiEvent.WordGroupNameInputCompletedEvent -> {
                event.uiScope.launch(Dispatchers.Default) {

                    event.pagerState.animateScrollToPage(event.pagerState.currentPage + 1)
                }
            }
        }
    }

    private val groupNameFlow:MutableStateFlow<String> = MutableStateFlow("")

    private val selectedImagePathFlow:MutableStateFlow<String?> = MutableStateFlow(null)

    override val state: Flow<ScreenState> = combine(
        groupNameFlow,selectedImagePathFlow
    ) { groupName,selectedImagePath ->
        ScreenState(groupName,selectedImagePath)
    }

    override val defValue: ScreenState
        get() = ScreenState()

}