package com.xxmrk888ytxx.viewgroupwordsscreen.models

sealed class SearchState {

    object Disabled : SearchState()

    data class Enabled(val searchValue:String = "") : SearchState()
}
