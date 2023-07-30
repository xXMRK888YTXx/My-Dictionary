package com.xxmrk888ytxx.settingsscreen

import android.content.Context
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.persistentListOf

internal fun LazyListScope.aboutApplicationCategory(
    versionName:String,
    onOpenSourceCode:() -> Unit,
    onOpenWriteToDeveloper:() -> Unit,
    onOpenPrivacyPolicy:() -> Unit,
    onOpenTermsOfUse:() -> Unit,
    context:Context
) {
    settingsCategory(
        title = context.getString(R.string.about_application),
        contents = persistentListOf(
            {
                LabelItem(
                    primaryText = stringResource(R.string.application_version),
                    secondaryText = versionName
                )
            },
            {
                ButtonItem(
                    text = stringResource(R.string.source_code),
                    onClick = onOpenSourceCode
                )
            },
            {
                ButtonItem(
                    text = stringResource(R.string.write_to_the_developer),
                    onClick = onOpenWriteToDeveloper
                )
            },
            {
                ButtonItem(
                    text = stringResource(R.string.privacy_policy),
                    onClick = onOpenPrivacyPolicy
                )
            },
            {
                ButtonItem(
                    text = stringResource(R.string.terms_of_use),
                    onClick = onOpenTermsOfUse
                )
            }
        )
    )
}

internal fun LazyListScope.backupCategory(
    onOpenCreateBackupScreen:() -> Unit,
    onOpenRestoreBackupScreen:() -> Unit,
    context: Context
) {
    settingsCategory(
        title = context.getString(R.string.backup),
        contents = persistentListOf(
            {
                ButtonItem(
                    text = stringResource(R.string.create_backup),
                    onClick = onOpenCreateBackupScreen
                )
            },
            {
                ButtonItem(
                    text = stringResource(R.string.restore_backup),
                    onClick = onOpenRestoreBackupScreen
                )
            },
        )
    )
}

internal fun LazyListScope.languageConfiguration(
    onOpenLanguageConfigurationScreen:() -> Unit,
    context: Context
) {
    settingsCategory(
        title = context.getString(R.string.languages),
        contents = persistentListOf(
            {
                ButtonItem(
                    text = stringResource(R.string.manage_languages),
                    onClick = onOpenLanguageConfigurationScreen
                )
            }
        )
    )
}

internal fun LazyListScope.advertisement(
    context: Context,
    onBuyRemoveAdRequest:() -> Unit,
    onRestorePurchases:() -> Unit
) {
    settingsCategory(
        title = context.getString(R.string.advertisement),
        contents = persistentListOf(
            {
                ButtonItem(
                    text = stringResource(R.string.remove_ads),
                    onClick = onBuyRemoveAdRequest
                )
            },
            {
                ButtonItem(
                    text = stringResource(R.string.restore_purchases),
                    onClick = onRestorePurchases
                )
            }
        )
    )
}