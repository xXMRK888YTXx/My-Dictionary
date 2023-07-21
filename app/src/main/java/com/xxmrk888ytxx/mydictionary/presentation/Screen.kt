package com.xxmrk888ytxx.mydictionary.presentation

sealed class Screen(val route:String) {

    object MainScreen : Screen("MainScreen")

    object CreateWordGroupScreen : Screen("CreateWordGroupScreen")

    object ViewGroupWordsScreen : Screen("ViewGroupWordsScreen")

    object EditWordScreen : Screen("AddWordScreen")

    object WordTranslateTrainingScreen : Screen("WordTranslateTrainingScreen")

    object WordByEarTrainingScreen : Screen("WordByEarTrainingScreen")

    object CreateBackupScreen : Screen("CreateBackupScreen")
}
