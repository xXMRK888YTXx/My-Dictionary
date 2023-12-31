package com.xxmrk888ytxx.settingsscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.settingsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.settingsscreen.models.ScreenState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


/**
 * [Ru]
 * Экран для управления настройками приложения
 */

/**
 * [En]
 * Screen for managing settings of application
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SettingsScreen(
    screenState: ScreenState,
    onEvent:(UiEvent) -> Unit
) {
    val navigator = LocalNavigator.current

    val context = LocalContext.current

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Scaffold(
        Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.settings)) },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddings ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {

            advertisement(
                context = context,
                onBuyRemoveAdRequest = { onEvent(LocalUiEvent.RequestBuyRemoveAdsEvent(context)) },
                onRestorePurchases = { onEvent(LocalUiEvent.RestorePurchasesEvent) },
                isAdsEnabled = screenState.isAdsEnabled
            )

            languageConfiguration(
                context = context,
                onOpenLanguageConfigurationScreen = { onEvent(LocalUiEvent.OpenLanguageManageScreen(navigator)) }
            )

            translatorSettingsCategory(
                context = context,
                openManageModelsForTranslateScreen = { onEvent(LocalUiEvent.OpenManageModelsForTranslateScreen(navigator)) }
            )

            backupCategory(
                onOpenCreateBackupScreen = { onEvent(LocalUiEvent.OpenCreateBackupScreenEvent(navigator)) },
                onOpenRestoreBackupScreen = { onEvent(LocalUiEvent.OpenRestoreBackupScreenEvent(navigator)) },
                onOpenAutoBackupToTelegramScreen = { onEvent(LocalUiEvent.OpenAutoBackupToTelegramEvent(navigator)) },
                context = context
            )

            aboutApplicationCategory(
                versionName = screenState.applicationVersion,
                onOpenSourceCode = { onEvent(LocalUiEvent.OpenSourceCodeEvent) },
                onOpenWriteToDeveloper = { onEvent(LocalUiEvent.WriteToDeveloperEvent) },
                onOpenPrivacyPolicy = { onEvent(LocalUiEvent.OpenPrivacyPolicy) },
                onOpenTermsOfUse = { onEvent(LocalUiEvent.OpenTermsOfUse) },
                context = context
            )
        }
    }
}
