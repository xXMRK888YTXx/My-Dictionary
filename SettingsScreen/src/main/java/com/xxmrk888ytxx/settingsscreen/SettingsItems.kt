package com.xxmrk888ytxx.settingsscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList

internal fun LazyListScope.settingsCategory(
    title:String,
    contents: ImmutableList<@Composable ColumnScope.() -> Unit>
) {
    item {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 10.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                contents.forEach {
                    it()
                }
            }
        }
    }
}

@Composable
fun ColumnScope.BaseItem(
    text:String,
    onClick: (() -> Unit)? = null,
    actionContent:@Composable () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier
                .weight(4f)
        )

        Box(
            modifier = Modifier
                .padding(start = 15.dp)
                .weight(1f)
        ) {
            actionContent()
        }

    }
}


@Composable
internal fun ColumnScope.LabelItem(
    primaryText:String,
    secondaryText:String
) {
    BaseItem(text = primaryText) {
        Text(
            text = secondaryText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
internal fun ColumnScope.ButtonItem(
    text:String,
    onClick:() -> Unit
) {
    BaseItem(
        text = text,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_chevron_right_24),
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
                .size(32.dp)
        )
    }
}