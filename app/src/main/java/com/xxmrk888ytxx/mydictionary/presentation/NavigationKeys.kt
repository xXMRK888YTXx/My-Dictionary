package com.xxmrk888ytxx.mydictionary.presentation

sealed class NavigationKeys(val key:String) {

    object WordGroupKeyForViewGroupWordsScreen : NavigationKeys("WordGroupKeyForViewGroupWordsScreen")

    object WordGroupKeyForEditWordScreen : NavigationKeys("WordGroupKeyForAddWordScreen")

    object EditWordIdForEditWordScreen : NavigationKeys("EditWordIdForAddWordScreen")

}
