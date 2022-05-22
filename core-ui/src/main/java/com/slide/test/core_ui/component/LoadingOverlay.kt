package com.slide.test.core_ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingOverlay(modifier: Modifier = Modifier, onDismissRequest:() -> Unit = {}) {
    Dialog(onDismissRequest = onDismissRequest) {
        LoadingWheel(contentDesc = "")
    }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        LoadingWheel(contentDesc = "")
    }
}