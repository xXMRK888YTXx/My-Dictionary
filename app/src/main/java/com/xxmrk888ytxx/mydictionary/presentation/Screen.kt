package com.xxmrk888ytxx.mydictionary.presentation

sealed class Screen(val route:String) {
    object WordGroupScreen : Screen("WordGroupScreen")
}
