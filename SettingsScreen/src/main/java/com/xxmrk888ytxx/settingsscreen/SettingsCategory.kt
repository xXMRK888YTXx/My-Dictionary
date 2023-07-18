package com.xxmrk888ytxx.settingsscreen

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.persistentListOf

internal fun LazyListScope.aboutApplicationCategory(
    versionName:String,
    onOpenSourceCode:() -> Unit,
    onOpenWriteToDeveloper:() -> Unit,
    onOpenPrivacyPolicy:() -> Unit,
    onOpenTermsOfUse:() -> Unit,
) {
    settingsCategory(
        title = "About application",
        contents = persistentListOf(
            {
                LabelItem(
                    primaryText = "Application version",
                    secondaryText = versionName
                )
            },
            {
                ButtonItem(
                    text = "Source code",
                    onClick = onOpenSourceCode
                )
            },
            {
                ButtonItem(
                    text = "Write to the developer",
                    onClick = onOpenWriteToDeveloper
                )
            },
            {
                ButtonItem(
                    text = "Privacy policy",
                    onClick = onOpenPrivacyPolicy
                )
            },
            {
                ButtonItem(
                    text = "Terms of use",
                    onClick = onOpenTermsOfUse
                )
            }
        )
    )
}