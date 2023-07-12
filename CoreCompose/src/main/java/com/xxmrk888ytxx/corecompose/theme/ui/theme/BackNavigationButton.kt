package com.xxmrk888ytxx.corecompose.theme.ui.theme

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.corecompose.R

@Composable
fun BackNavigationButton(
    onClick:() -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "",
            modifier = Modifier
                .size(28.dp)
        )
    }
}