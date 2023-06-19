package com.xxmrk888ytxx.mydictionary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.xxmrk888ytxx.androidcore.runOnUiThread
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import javax.inject.Inject
import javax.inject.Provider

class ActivityViewModel @Inject constructor(

) : ViewModel(),Navigator {

    private val navController:NavController? = null


    override fun toCreateWordGroupScreen() = runOnUiThread {
        navigate(Screen.CreateWordGroupScreen)
    }

    override fun backScreen() = runOnUiThread {
        navController?.navigateUp()
    }


    private fun navigate(screen:Screen) {
        if(navController == null) return

        navController.navigate(screen.route) {
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