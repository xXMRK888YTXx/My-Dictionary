package com.xxmrk888ytxx.mydictionary.presentation

sealed class Screen(val route:String) {
    object WordGroupScreen : Screen("WordGroupScreen")

    object CreateWordGroupScreen : Screen("CreateWordGroupScreen")

    object ViewGroupWordsScreen : Screen("ViewGroupWordsScreen")

    object AddWordScreen : Screen("AddWordScreen")
}
