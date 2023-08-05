package com.xxmrk888ytxx.corecompose.theme.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.xxmrk888ytxx.corecompose.theme.ui.theme.models.BottomSheetDialogItem
import kotlinx.collections.immutable.ImmutableList

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialog(
    onDismiss: () -> Unit,
    items:ImmutableList<BottomSheetDialogItem>
) {
    val state = rememberBottomSheetScaffoldState()

    LaunchedEffect(key1 = state, block = {
        state.bottomSheetState.show()
    })

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BottomSheetScaffold(
            sheetContent = {
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    items(items) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clickable(
                                    onClick = it.onClick
                                )
                        ) {
                            Icon(
                                painter = painterResource(id = it.icon),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp)
                            )

                            Text(text = it.text)
                        }
                    }
                }
            },
            scaffoldState = state,
            sheetPeekHeight = LocalConfiguration.current.screenHeightDp.dp * 0.25f,
        ) {}
    }
}