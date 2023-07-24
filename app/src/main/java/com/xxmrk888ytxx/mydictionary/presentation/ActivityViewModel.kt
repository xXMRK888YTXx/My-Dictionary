package com.xxmrk888ytxx.mydictionary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.xxmrk888ytxx.androidcore.runOnUiThread
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import com.xxmrk888ytxx.texttospeechmanager.TTSManager
import javax.inject.Inject
import javax.inject.Provider

class ActivityViewModel @Inject constructor(
    private val ttsManager: TTSManager
) : ViewModel(),Navigator {

    var navController:NavController? = null

    fun initTTS() {
        ttsManager.init()
    }


    override fun toCreateWordGroupScreen() = runOnUiThread {
        navigate(Screen.CreateWordGroupScreen)
    }

    override fun toViewGroupWordsScreen(wordGroupId: Int) = runOnUiThread {
        navController?.navigate(
            route = "${Screen.ViewGroupWordsScreen.route}/$wordGroupId"
        ) {
            launchSingleTop = true
        }
    }

    override fun toEditWordScreen(wordGroupId: Int,editWordId:Int) = runOnUiThread {
        navController?.navigate(
            route = "${Screen.EditWordScreen.route}/$wordGroupId/$editWordId"
        ) {
            launchSingleTop = true
        }
    }

    override fun backScreen() = runOnUiThread {
        navController?.navigateUp()
    }

    override fun toWordTranslateTraining() = runOnUiThread {
        navController?.navigate(Screen.WordTranslateTrainingScreen.route) {
            launchSingleTop = true
        }
    }

    override fun toWordByEarTraining() = runOnUiThread {
        navController?.navigate(Screen.WordByEarTrainingScreen.route) {
            launchSingleTop = true
        }
    }

    override fun toCreateBackupScreen() = runOnUiThread {
        navigate(Screen.CreateBackupScreen)
    }

    override fun toRestoreBackupScreen() {
        navigate(Screen.RestoreBackupScreen)
    }


    private fun navigate(screen:Screen) {
        navController?.navigate(screen.route) {
            launchSingleTop = true
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModel:Provider<ActivityViewModel>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModel.get() as T
        }
    }
}